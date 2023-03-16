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
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentAddExpenseBinding
import com.alhanpos.store.model.request.expense.ExpenseRequest
import com.alhanpos.store.model.request.expense.Payment
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Callback
import com.alhanpos.store.util.FileUtils.getPath
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddExpenseViewModel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            if (TextUtils.isEmpty(binding.edtTotal.text.toString().trim())) {
                binding.edtDate.error = "Total Amount cannot be empty"
                binding.edtDate.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtAmt.text.toString().trim())) {
                binding.edtDate.error = "Amount cannot be empty"
                binding.edtDate.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtPaidOn.text.toString().trim())) {
                binding.edtPaidOn.error = "Paid on date cannot be empty"
                binding.edtPaidOn.requestFocus()
                return@setOnClickListener
            }

            val payments = arrayListOf<Payment>()
            payments.add(
                Payment(
                    accountID,
                    binding.edtAmt.text.toString().trim(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    binding.spinnerPaymentMethod.selectedItem.toString(),
                    "",
                    "",
                )
            )


            val jsonData = ExpenseRequest(
                binding.edtExpenseNote.text.toString().trim(),
//                "0.0",
                contact_ID.toInt(),
//                "percentage",
                40,
                54,
                1,
                binding.edtAmt.text.toString().trim(),
                "0",
                location_ID.toInt(),
                payments,
                binding.edtRecurringInterval.text.toString().trim(),
                "days",
                binding.edtNoOfRep.text.toString().trim(),
                binding.edtRefNO.text.toString().trim(),
//                "",
                "",
                "final",
                "",
//                0,
                3,
                binding.edtDate.text.toString().trim()
            )
            val json = Gson().toJson(jsonData)
            val data = RequestBody.create(MultipartBody.FORM, json)
            val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), file!!)
            val document = MultipartBody.Part.createFormData("document", file!!.name, requestBody)

            viewModel.addUpdateExpense(
                "Bearer " + prefs.accessToken, document, data
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

    private fun updateLabel(isPaidOn: Boolean) {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        if (isPaidOn)
            binding.edtPaidOn.setText(dateFormat.format(myCalendar.time))
        else
            binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }
}