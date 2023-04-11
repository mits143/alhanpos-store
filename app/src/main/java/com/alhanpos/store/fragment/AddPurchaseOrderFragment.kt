package com.alhanpos.store.fragment

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.AddStockTransferAdapter
import com.alhanpos.store.databinding.FragmentAddPurchaseOrderBinding
import com.alhanpos.store.model.request.purchase.Product
import com.alhanpos.store.model.request.purchase.PurchaseRequest
import com.alhanpos.store.model.response.product.ProductListResponse.ProductListResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Callback
import com.alhanpos.store.util.FileUtils.getPath
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddExpenseViewModel
import com.alhanpos.store.viewmodel.AddPurchaseViewModel
import com.alhanpos.store.viewmodel.AddStockTransferViewModel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddPurchaseOrderFragment : BaseFragment<FragmentAddPurchaseOrderBinding>(),
    AddStockTransferAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPurchaseOrderBinding =
        FragmentAddPurchaseOrderBinding::inflate

    private val viewModel: AddPurchaseViewModel by viewModel()
    private var locationList: ArrayList<AddPurchaseViewModel.Common> = arrayListOf()
    private var productDataList: ArrayList<ProductListResponseItem> = arrayListOf()
    var productList: ArrayList<AddStockTransferViewModel.product> = ArrayList()
    private var posList: ArrayList<ProductListResponseItem> = arrayListOf()
    private var paymentAccountList: ArrayList<AddExpenseViewModel.Common> = arrayListOf()
    private var paymentMethodList: ArrayList<String> = arrayListOf()
    lateinit var adapter: AddStockTransferAdapter
    private var location_ID = "0"
    private var accountID = "0"
    private var supplier_ID = "0"
    private var file: File? = null
    var sku = ""
    var type = 0

    val myCalendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        callbacks()

        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = day
            updateLabel(false)
        }

        val paidOn = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = day
            updateLabel(true)
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
        binding.edtPaidOn.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                paidOn,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.edtDoc.setOnClickListener {
            type = 1
            getPermission()
        }

        binding.flScan.setOnClickListener {
            type = 2
            getPermission()
        }

        binding.txtProceed.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtRefNO.text.toString().trim())) {
                binding.edtRefNO.error = "Reference number cannot be empty"
                binding.edtRefNO.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtDate.text.toString().trim())) {
                binding.edtDate.error = "Date cannot be empty"
                binding.edtDate.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtTerm.text.toString().trim())) {
                binding.edtTerm.error = "Term cannot be empty"
                binding.edtTerm.requestFocus()
                return@setOnClickListener
            }

            if (posList.isNotEmpty()) {
                val products = arrayListOf<Product>()
                var finalAmt = "0"
                posList.forEach {
                    products.add(
                        Product(
                            "",
                            binding.edtAmt.text.toString().trim(),
                            it.sellingPrice.toString(),
                            binding.spinnerPaymentMethod.selectedItem.toString().trim(),
                            "",
                            binding.edtPaymenNote.text.toString().trim(),
                            binding.edtPaidOn.text.toString().trim(),
                            it.productId.toString(),
                            "1",
                            "3",
                            "10",
                            "11.00",
                            it.quantity.toString(),
                            "1",
                            it.variationId.toString()
                        )
                    )
                    finalAmt += it.price
                }

                val jsonData = PurchaseRequest(
//                    "0.00",
                    supplier_ID,
                    binding.edtDiscountAmt.text.toString().trim(),
                    binding.edtDiscountType.selectedItem.toString().trim(),
                    1,
                    40,
                    "1",
                    1,
                    finalAmt,
                    binding.edtTerm.text.toString().trim().toInt(),
                    0,
                    products,
                    binding.edtRefNO.text.toString().trim(),
                    binding.edtShippingCharges.text.toString().trim(),
                    binding.edtShippingDetails.text.toString().trim(),
                    binding.spinStatus.selectedItem.toString().trim(),
                    "0.10",
                    "3",
                    binding.edtDate.text.toString().trim()
                )
                val json = Gson().toJson(jsonData)
                val data = RequestBody.create(MultipartBody.FORM, json)
                val document: MultipartBody.Part
                if (file != null) {
                    val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), file!!)
                    document = createFormData("document", file!!.name, requestBody)

                } else {
                    val attachmentEmpty = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
                    document = createFormData("document", "", attachmentEmpty)
                }
                viewModel.addUpdatePurchase(
                    "Bearer " + prefs.accessToken, document, data
                )
            } else {
                showToast("Please select product for purchase order")
            }
        }
    }

    private fun setLocationData(dataList: ArrayList<AddPurchaseViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLocation.adapter = adapter
        binding.spinLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                location_ID = (adapter.getItem(position) as AddPurchaseViewModel.Common).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setSupplierData(dataList: ArrayList<AddPurchaseViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinSupplier.adapter = adapter
        binding.spinSupplier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                supplier_ID = (adapter.getItem(position) as AddPurchaseViewModel.Common).id
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

    private fun setPaymentMethodData(paymentMethodList: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, paymentMethodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentMethod.adapter = adapter
    }

    private fun setPaymentAccountData(paymentAccountList: ArrayList<AddExpenseViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, paymentAccountList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentAccount.adapter = adapter
        binding.spinnerPaymentAccount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    accountID = (adapter.getItem(position) as AddExpenseViewModel.Common).id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setObserver() {
        viewModel.fetchSupplier("Bearer " + prefs.accessToken)
        viewModel.fetchProduct("Bearer " + prefs.accessToken!!, prefs.sku.toString())
        viewModel.fetchPaymentAccountData("Bearer " + prefs.accessToken!!)
        viewModel.fetchPaymentMethodData("Bearer " + prefs.accessToken!!)
        viewModel.getPaymentAccountData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        paymentAccountList.clear()
                        it.data.forEach {
                            paymentAccountList.add(
                                AddExpenseViewModel.Common(
                                    it.name, it.id.toString()
                                )
                            )
                        }
                        setPaymentAccountData(paymentAccountList)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }
        viewModel.getPaymentMethodData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        paymentMethodList.clear()
                        paymentMethodList.add(it.cash)
                        paymentMethodList.add(it.card)
                        paymentMethodList.add(it.cheque)
                        paymentMethodList.add(it.bankTransfer)
                        setPaymentMethodData(paymentMethodList)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
        viewModel.getContactData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
//                        adapter.addData(it.data)
                        val dataList = arrayListOf<AddPurchaseViewModel.Common>()
                        for (i in it.data.indices) {
                            if (!it.data[i].name.isNullOrEmpty()) {
                                dataList.add(
                                    AddPurchaseViewModel.Common(
                                        it.data[i].name!!, it.data[i].id.toString()
                                    )
                                )
                            }
                        }

                        setSupplierData(dataList)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
            viewModel.fetchLocation("Bearer " + prefs.accessToken!!)
        }

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
                                AddPurchaseViewModel.Common(
                                    it.name, it.id.toString()
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
                            productDataList.addAll(it.data)
                            it.data.forEach {
                                productList.add(
                                    AddStockTransferViewModel.product(
                                        it.name!!, it.subSku!!
                                    )
                                )
                            }
                            if (productDataList.size == 1) {
                                posList.addAll(productDataList)
                                setPosData(posList)
                            } else setProductData(productList)
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
                        binding.edtDoc.setText("")
                        binding.edtTerm.setText("")
                        binding.edtShippingDetails.setText("")
                        binding.edtShippingCharges.setText("")
                        posList.clear()
                        adapter.notifyDataSetChanged()
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
                val path = getPath(requireContext(), uri!!)
                file = File(path!!)
                binding.edtDoc.setText(file?.name.toString().trim())
            }

            override fun browseImageData(uri: Uri?) {
                val path = getPath(requireContext(), uri!!)
                file = File(path!!)
                binding.edtDoc.setText(file?.name.toString().trim())
            }

            override fun pdfData(uri: Uri?) {
            }

            override fun permissionGranted() {
                if (type == 1) selectImage()
                else {
                    val action =
                        AddPurchaseOrderFragmentDirections.actionNavAddPurchaseOrderToNavScanner()
                    findNavController().navigate(action)
                }
            }

            override fun permissionNotGranted() {
            }

        })
    }

    private fun updateLabel(isPaidOn: Boolean) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        if (isPaidOn) binding.edtPaidOn.setText(dateFormat.format(myCalendar.time))
        else binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }

    override fun onClick(data: ArrayList<ProductListResponseItem>, position: Int) {
        productDataList.forEach { outer ->
            data.forEach { inner ->
                if (outer.productId.equals(inner.productId) && outer.isAdded) outer.isAdded = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prefs.sku = ""
    }

}