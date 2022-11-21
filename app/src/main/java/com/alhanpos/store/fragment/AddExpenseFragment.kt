package com.alhanpos.store.fragment

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentAddExpenseBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Callback
import com.alhanpos.store.util.FileUtils
import com.alhanpos.store.util.FileUtils.getPath
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddExpenseViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddExpenseFragment : BaseFragment<FragmentAddExpenseBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddExpenseBinding =
        FragmentAddExpenseBinding::inflate

    private val viewModel: AddExpenseViewModel by viewModel()
    private var locationList: ArrayList<AddExpenseViewModel.Common> = arrayListOf()
    private var paymentAccountList: ArrayList<AddExpenseViewModel.Common> = arrayListOf()
    private var paymentMethodList: ArrayList<String> = arrayListOf()
    private var location_ID = "0"
    private var contact_ID = "0"
    private var accountID = "0"
    private var file: File? = null


    val myCalendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        callbacks()

        val date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
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
        binding.edtPaidOn.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.edtDoc.setOnClickListener {
            getPermission()
        }

        binding.txtProceed.setOnClickListener {
//            if (TextUtils.isEmpty(binding.edtBrandName.text.toString().trim())) {
//                binding.edtBrandName.error = "Category name cannot be empty"
//                binding.edtBrandName.requestFocus()
//                return@setOnClickListener
//            }

            val location_id = location_ID.toRequestBody(MultipartBody.FORM)
            val expense_category_id = "40".toRequestBody(MultipartBody.FORM)
            val expense_sub_category_id = "".toRequestBody(MultipartBody.FORM)
            val ref_no = binding.edtRefNO.text.toString().trim().toRequestBody(MultipartBody.FORM)
            val transaction_date =
                binding.edtDate.text.toString().trim().toRequestBody(MultipartBody.FORM)
            val expense_for = "".toRequestBody(MultipartBody.FORM)
            val contact_id = contact_ID.toRequestBody(MultipartBody.FORM)

            val requestFile = file!!.asRequestBody(FileUtils.MIME_TYPE_IMAGE.toMediaTypeOrNull())
            val document = MultipartBody.Part.createFormData(
                "document", file?.name, requestFile
            )
            val tax_id = "".toRequestBody(MultipartBody.FORM)
            val tax_calculation_amount = "0".toRequestBody(MultipartBody.FORM)
            val recur_interval = "0".toRequestBody(MultipartBody.FORM)
            val recur_interval_type = "days".toRequestBody(MultipartBody.FORM)
            val recur_repetitions = "0".toRequestBody(MultipartBody.FORM)
            val final_total =
                binding.edtTotal.text.toString().trim().toRequestBody(MultipartBody.FORM)
            val additional_notes =
                binding.edtExpenseNote.text.toString().trim().toRequestBody(MultipartBody.FORM)
            val paymentamount: RequestBody =
                binding.edtAmt.text.toString().trim().toRequestBody(MultipartBody.FORM)
            val paymentpaid_on: RequestBody =
                binding.edtPaidOn.text.toString().trim().toRequestBody(MultipartBody.FORM)

            val paymentmethod = binding.spinnerPaymentMethod.selectedItem.toString().trim()
                .toRequestBody(MultipartBody.FORM)
            val paymentaccount_id = accountID.toRequestBody(MultipartBody.FORM)
            val paymentnote: RequestBody =
                binding.edtPaymenNote.text.toString().trim().toRequestBody(MultipartBody.FORM)

            viewModel.addUpdateExpense(
                "Bearer " + prefs.accessToken,
                location_id,
                expense_category_id,
                expense_sub_category_id,
                ref_no,
//                transaction_date,
                expense_for,
                contact_id,
                document,
                tax_id,
                tax_calculation_amount,
                recur_interval,
                recur_interval_type,
                recur_repetitions,
                final_total,
                additional_notes,
                paymentamount,
                paymentpaid_on,
                paymentmethod,
                paymentaccount_id,
                paymentnote
            )
        }
    }

    private fun setLocationData(dataList: ArrayList<AddExpenseViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLocation.adapter = adapter
        binding.spinLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                location_ID = (adapter.getItem(position) as AddExpenseViewModel.Common).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setSupplierData(dataList: ArrayList<AddExpenseViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinContact.adapter = adapter
        binding.spinContact.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                contact_ID = (adapter.getItem(position) as AddExpenseViewModel.Common).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

    private fun setPaymentMethodData(paymentMethodList: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, paymentMethodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentMethod.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchContact("Bearer " + prefs.accessToken)
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
                        val dataList = arrayListOf<AddExpenseViewModel.Common>()
                        for (i in it.data.indices) {
                            if (!it.data[i].name.isNullOrEmpty()) {
                                dataList.add(
                                    AddExpenseViewModel.Common(
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
                                AddExpenseViewModel.Common(
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
                        binding.edtTotal.setText("")
                        binding.edtExpenseNote.setText("")
                        binding.edtAmt.setText("")
                        binding.edtPaidOn.setText("")
                        binding.edtPaymenNote.setText("")
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
                selectImage()
            }

            override fun permissionNotGranted() {
            }

        })
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
        binding.edtPaidOn.setText(dateFormat.format(myCalendar.time))
    }
}