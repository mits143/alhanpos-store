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
import com.alhanpos.store.databinding.FragmentAddStockTransferBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddStockAdjustmentViewModel
import com.alhanpos.store.viewmodel.AddStockTransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddStockTransferFragment : BaseFragment<FragmentAddStockTransferBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddStockTransferBinding =
        FragmentAddStockTransferBinding::inflate

    private val viewModel: AddStockTransferViewModel by viewModel()

    val myCalendar = Calendar.getInstance()
    private var locationList: ArrayList<AddStockAdjustmentViewModel.Common> = arrayListOf()
    private var location_ID_FROM = "0"
    private var location_ID_TO = "0"

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
                location_ID_FROM,
                binding.edtShippingCharges.text.toString().trim()
            )
        }
    }


    private fun setLocationData(dataList: ArrayList<AddStockAdjustmentViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLocationFrom.adapter = adapter
        binding.spinLocationFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    location_ID_FROM =
                        (adapter.getItem(position) as AddStockAdjustmentViewModel.Common).id
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
                        (adapter.getItem(position) as AddStockAdjustmentViewModel.Common).id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setObserver() {
        viewModel.fetchLocation("Bearer " + prefs.accessToken!!)

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
                                AddStockAdjustmentViewModel.Common(
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
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }
}