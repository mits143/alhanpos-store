package com.alhanpos.store.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alhanpos.store.R
import com.alhanpos.store.adapter.AddStockTransferAdapter
import com.alhanpos.store.databinding.FragmentAddStockTransferBinding
import com.alhanpos.store.model.response.product.ProductListResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddStockTransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddStockTransferFragment : BaseFragment<FragmentAddStockTransferBinding>(),
    AddStockTransferAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddStockTransferBinding =
        FragmentAddStockTransferBinding::inflate

    private val viewModel: AddStockTransferViewModel by viewModel()

    val myCalendar = Calendar.getInstance()
    private var locationList: ArrayList<AddStockTransferViewModel.Common> = arrayListOf()
    private var productDataList: ArrayList<ProductListResponseItem> = arrayListOf()
    var productList: ArrayList<AddStockTransferViewModel.product> = ArrayList()
    private var posList: ArrayList<ProductListResponseItem> = arrayListOf()
    lateinit var adapter: AddStockTransferAdapter
    private var location_ID_FROM = "0"
    private var location_ID_TO = "0"
    var sku = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }
        binding.edtDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.txtProceed.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtRefNO.text.toString().trim())) {
                binding.edtRefNO.error = "Reference No. cannot be empty"
                binding.edtRefNO.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtDate.text.toString().trim())) {
                binding.edtDate.error = "Date cannot be empty"
                binding.edtDate.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtShippingDetails.text.toString().trim())) {
                binding.edtShippingDetails.error = "Shipping details cannot be empty"
                binding.edtShippingDetails.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtShippingCharges.text.toString().trim())) {
                binding.edtShippingCharges.error = "Shipping charges cannot be empty"
                binding.edtShippingCharges.requestFocus()
                return@setOnClickListener
            }
            viewModel.addUpdateStockTransfer(
                "Bearer " + prefs.accessToken,
                binding.edtDate.text.toString().trim(),
                binding.edtRefNO.text.toString().trim(),
                binding.spinStatus.selectedItem.toString().trim(),
                "0",
                location_ID_TO,
                binding.edtShippingCharges.text.toString().trim(),
                location_ID_FROM,
                "",
                posList[0].productId,
                posList[0].variationId,
                posList[0].enableStock,
                posList[0].quantity.toString(),
                "1",
                "1",
                "1",
                posList[0].sellingPrice,
                posList[0].price,
                ""
            )
        }
    }


    private fun setLocationData(dataList: ArrayList<AddStockTransferViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLocationFrom.adapter = adapter
        binding.spinLocationFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    location_ID_FROM =
                        (adapter.getItem(position) as AddStockTransferViewModel.Common).id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        binding.spinLocationTO.adapter = adapter
        binding.spinLocationTO.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    location_ID_TO =
                        (adapter.getItem(position) as AddStockTransferViewModel.Common).id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setProductData(productList: ArrayList<AddStockTransferViewModel.product>) {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, productList)
        binding.spinnerProduct.threshold = 2
        binding.spinnerProduct.setAdapter(adapter)
        binding.spinnerProduct.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, long ->
                sku = (adapter.getItem(position) as AddStockTransferViewModel.product).sku
                if (productDataList.isNotEmpty() && sku.isNotEmpty()) {
                    for (i in productDataList.indices) {
                        if (TextUtils.equals(productDataList[i].subSku, sku)) {
                            if (!productDataList[i].isAdded) {
                                productDataList[i].isAdded = true
                                posList.add(productDataList[i])
                            } else {
                                showToast("Product already added")
                            }
                        }
                    }
                    setPosData(posList)
                    binding.spinnerProduct.setText("")
                }
            }
    }

    private fun setPosData(posList: ArrayList<ProductListResponseItem>) {
        adapter = AddStockTransferAdapter(posList, this)
        binding.rVCategory.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchLocation("Bearer " + prefs.accessToken!!)
        viewModel.fetchProduct("Bearer " + prefs.accessToken!!)

        viewModel.getLocationData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        locationList.clear()
                        it.data.forEach {
                            locationList.add(
                                AddStockTransferViewModel.Common(
                                    it.name,
                                    it.id.toString()
                                )
                            )
                        }
                        setLocationData(locationList)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
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
                            productList.clear()
                            productDataList.addAll(it)
                            it.forEach {
                                productList.add(
                                    AddStockTransferViewModel.product(
                                        it.name,
                                        it.subSku
                                    )
                                )
                            }
                            setProductData(productList)
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
        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        showToast(it)
                        binding.edtRefNO.setText("")
                        binding.edtDate.setText("")
                        binding.edtShippingDetails.setText("")
                        binding.edtShippingCharges.setText("")
                        posList.clear()
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }

    override fun onClick(data: ArrayList<ProductListResponseItem>, position: Int) {
    }
}