package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.PosAdapter
import com.alhanpos.store.databinding.FragmentPosBinding
import com.alhanpos.store.model.response.product.ProductData
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PosFragment : BaseFragment<FragmentPosBinding>(), PosAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosBinding =
        FragmentPosBinding::inflate

    private val viewModel: PosViewModel by viewModel()

    private var locationList: ArrayList<String> = arrayListOf()
    private var contactList: ArrayList<String> = arrayListOf()
    private var productDataList: ArrayList<ProductData> = arrayListOf()
    var productList: ArrayList<PosViewModel.product> = ArrayList()

    private var posList: ArrayList<ProductData> = arrayListOf()

    var sku = ""
    var subTotal = 0.0
    var tax = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.txtProceed.setOnClickListener {
            findNavController().navigate(R.id.action_nav_pos_to_nav_pos_payment)
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
            ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, contactList)
        binding.spinnerType.threshold = 2
        binding.spinnerType.setAdapter(adapter)
        binding.spinnerType.setText(contactList[0])
    }

    private fun setProductData(productList: ArrayList<PosViewModel.product>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, productList)
        binding.spinnerProduct.threshold = 2
        binding.spinnerProduct.setAdapter(adapter)
        binding.spinnerProduct.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, long ->
                sku = (adapter.getItem(position) as PosViewModel.product).sku
                if (productDataList.isNotEmpty() && sku.isNotEmpty()) {
                    for (i in productDataList.indices) {
                        if (TextUtils.equals(productDataList[i].sku, sku)) {
                            posList.add(productDataList[i])
                        }
                    }
                    setPosData(posList)
                    binding.spinnerProduct.setText("")
                }
            }
    }

    private fun setPosData(posList: ArrayList<ProductData>) {
        val adapter = PosAdapter(posList, this)
        binding.rvProduct.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchLocation("Bearer " + prefs.accessToken!!)
        viewModel.fetchContact("Bearer " + prefs.accessToken!!)
        viewModel.fetchProduct("Bearer " + prefs.accessToken!!)

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
//                        locationList.add(0, "Select Location")
                        setLocationData(locationList)

                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
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
                            contactList.add(it.name)
                        }
//                        contactList.add(0, "Select Customer")
                        setContactData(contactList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }

        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        productDataList.clear()
                        productList.clear()
                        productDataList.addAll(it.data)
                        it.data.forEach {
                            productList.add(PosViewModel.product(it.name, it.sku))
                        }
                        setProductData(productList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }
    }

    private fun setTotal() {
//        binding.txtSubTotal.text = price.toString()
//        binding.txtTax.text = tax.toString()
//        binding.txtTotal.text = (price.toFloat() + tax.toFloat()).toString()
    }

    var price = "0"
    override fun onClick(data: ArrayList<ProductData>, position: Int) {
        for (i in data.indices)
            price = data[position].price
        binding.txtSubTotal.text = price
    }
}