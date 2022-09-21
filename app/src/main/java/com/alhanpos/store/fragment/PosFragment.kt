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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.txtProceed.setOnClickListener {
            findNavController().navigate(R.id.action_nav_pos_to_nav_pos_payment)
        }
    }

    private fun setLocationData(locationList: ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, locationList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLocation.adapter = adapter

        binding.spinnerLocation.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
    }

    private fun setContactData(contactList: ArrayList<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, contactList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.spinnerType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
    }

    private fun setProductData(productList: ArrayList<PosViewModel.product>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, productList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProduct.adapter = adapter

        binding.spinnerProduct.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    sku = productList[position].sku
                    if (productDataList.isNotEmpty() && sku.isNotEmpty()) {
                        for (i in productDataList.indices) {
                            if (TextUtils.equals(productDataList[i].sku, sku)) {
                                posList.add(productDataList[i])
                            }
                        }
                        setPosData(posList)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

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

                        productList.add(0, PosViewModel.product("Select Product", ""))
                        setProductData(productList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }
    }

    override fun decrease(data: ProductData) {
        data.quantity = if (data.quantity > 1) data.quantity - 1 else 1
//        binding.txtQty.setText(quantity.toString())
//        data.price =
//            (data.quantity.toFloat() * data.product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()
//        binding.txtPrice.text = price
    }

    override fun increase(data: ProductData) {
        data.quantity += 1
//        binding.txtQty.setText(quantity.toString())
//        data.price =
//            (data.quantity.toFloat() * data.product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()
//        binding.txtPrice.text = price
    }

    override fun quantity(data: ProductData, value: Int) {
        data.quantity = value
        data.price =
            (data.quantity.toFloat() * data.product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()
//        binding.txtPrice.text = price
    }
}