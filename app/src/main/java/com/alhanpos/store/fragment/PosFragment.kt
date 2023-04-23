package com.alhanpos.store.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.PosAdapter
import com.alhanpos.store.databinding.FragmentPosBinding
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.model.response.product.ProductListResponse.ProductListResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Callback
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PosFragment : BaseFragment<FragmentPosBinding>(), PosAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosBinding =
        FragmentPosBinding::inflate

    private val viewModel: PosViewModel by viewModel()

    private var locationList: ArrayList<String> = arrayListOf()
    private var contactList: ArrayList<String> = arrayListOf()
    private var productDataList: ArrayList<ProductListResponseItem> = arrayListOf()
    private var productDataListTemp: ArrayList<ProductListResponseItem> = arrayListOf()
    var productList: ArrayList<PosViewModel.product> = ArrayList()
    lateinit var adapter: PosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        callbacks()
        setObserver()

        binding.searchView.setOnQueryTextListener(this)
        binding.txtProceed.setOnClickListener {
            if (productDataList.isNotEmpty()) {
                productDataListTemp = arrayListOf()
                productDataList.forEach {
                    if (it.isAdded) productDataListTemp.add(it)
                }
            }

            if (productDataListTemp.isNotEmpty()) {
                val productListResponse = ProductListResponse(productDataListTemp)
                val action = PosFragmentDirections.actionNavPosToNavPosPayment(
                    productListResponse
                )
                findNavController().navigate(action)
            } else {
                showToast("Please select atleast one product")
            }
        }

        binding.flScan.setOnClickListener {
            getPermission()
        }
    }

    private fun setLocationData(locationList: ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, locationList)
        binding.spinnerLocation.threshold = 2
        binding.spinnerLocation.setAdapter(adapter)
        binding.spinnerLocation.setText(locationList[0])
    }

    private fun setContactData(contactList: ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, contactList)
        binding.spinnerType.threshold = 2
        binding.spinnerType.setAdapter(adapter)
        binding.spinnerType.setText(contactList[0])
    }

    private fun setPosData(posList: ArrayList<ProductListResponseItem>) {
        adapter = PosAdapter(posList, this)
        binding.rvProduct.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchLocation("Bearer " + prefs.accessToken!!)
        viewModel.fetchContact("Bearer " + prefs.accessToken!!)
        if (prefs.getArrayList().isNotEmpty()) {
            setPosData(prefs.getArrayList())
        } else {
            viewModel.fetchProduct("Bearer " + prefs.accessToken!!, "", prefs.sku.toString())
        }

        viewModel.getLocationData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        locationList.clear()
                        it.data.forEach {
                            locationList.add(it.name)
                        }
                        setLocationData(locationList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }

        viewModel.getContactData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        contactList.clear()
                        it.data.forEach {
                            contactList.add(it.name!!)
                        }
                        setContactData(contactList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }

        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    if (it.data != null) {
                        it.data.let {
                            binding.animationView.visibility = View.GONE
                            productDataList.clear()
                            productDataList.addAll(it.data)
                            setPosData(productDataList)
                        }
                    } else {
                        showToast("No data available")
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    private fun callbacks() {
        setUpListener(object : Callback {
            override fun captureImageData(uri: Uri?) {
            }

            override fun browseImageData(uri: Uri?) {
            }

            override fun pdfData(uri: Uri?) {
            }

            override fun permissionGranted() {
                val action = PosFragmentDirections.actionNavPosToNavScanner()
                findNavController().navigate(action)
            }

            override fun permissionNotGranted() {
            }

        })
    }

    override fun onClick(dataList: ProductListResponseItem, position: Int) {
        dataList.isAdded = !dataList.isAdded
        adapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.toString().isNotEmpty())
            viewModel.fetchProduct("Bearer " + prefs.accessToken!!, prefs.sku.toString(), newText!!)
        return false
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getArrayList().isNotEmpty()) {
            setPosData(prefs.getArrayList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prefs.saveArrayList(arrayListOf())
    }
}