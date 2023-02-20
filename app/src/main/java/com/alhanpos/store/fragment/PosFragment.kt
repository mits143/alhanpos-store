package com.alhanpos.store.fragment

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
import com.alhanpos.store.model.response.product.ProductListResponseItem
import com.alhanpos.store.prefs
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
    var productList: ArrayList<PosViewModel.product> = ArrayList()

    private var posList: ArrayList<ProductListResponseItem> = arrayListOf()
    lateinit var adapter: PosAdapter

    var sku = ""
    var totalItems = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

//        if (productList.isNotEmpty()) setProductData(productList)

        binding.searchView.setOnQueryTextListener(this)
        binding.txtProceed.setOnClickListener {
            if (productDataList.isNotEmpty()) {
                val productListResponse = ProductListResponse(productDataList)
                val action = PosFragmentDirections.actionNavPosToNavPosPayment(
                    productListResponse
                )
                findNavController().navigate(action)
            } else {
                showToast("Please select atleast one product")
            }
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

//    private fun setProductData(productList: ArrayList<PosViewModel.product>) {
//        val adapter =
//            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, productList)
//        binding.spinnerProduct.threshold = 2
//        binding.spinnerProduct.setAdapter(adapter)
//        binding.spinnerProduct.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, view, position, long ->
//                sku = (adapter.getItem(position) as PosViewModel.product).sku
//                if (productDataList.isNotEmpty() && sku.isNotEmpty()) {
//                    for (i in productDataList.indices) {
//                        if (TextUtils.equals(productDataList[i].subSku, sku)) {
//                            if (!productDataList[i].isAdded) {
//                                productDataList[i].isAdded = true
//                                posList.add(productDataList[i])
//                            } else {
//                                showToast("Product already added")
//                            }
//                        }
//                    }
////                    setPosData(posList)
//                    binding.spinnerProduct.setText("")
//                }
//            }
//    }

    private fun setPosData(posList: ArrayList<ProductListResponseItem>) {
        adapter = PosAdapter(posList, this)
        binding.rvProduct.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchLocation("Bearer " + prefs.accessToken!!)
        viewModel.fetchContact("Bearer " + prefs.accessToken!!)
        viewModel.fetchProduct("Bearer " + prefs.accessToken!!, "")

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
//                            productList.clear()
                            productDataList.addAll(it.data)
//                            it.data.forEach {
//                                productList.add(PosViewModel.product(it.name!!, it.subSku!!))
//                            }
                            setPosData(productDataList)
//                            setProductData(productList)
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

    override fun onClick(dataList: ProductListResponseItem, position: Int) {
        dataList.isAdded = !dataList.isAdded
        adapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.fetchProduct("Bearer " + prefs.accessToken!!, newText!!)
        return false
    }

//    override fun onClick(dataList: ArrayList<ProductListResponseItem>, position: Int) {
//        var total = 0f
//        for (i in 0 until dataList.size) {
//            total += (dataList[i].sellingPrice!!.toFloat() * dataList[i].quantity.toFloat())
//        }
//
//        totalItems = dataList.size.toString()
//        binding.txtSubTotal.text = total.toString()
//        binding.txtTotal.text = total.toString()
//    }
}