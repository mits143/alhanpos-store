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
import com.alhanpos.store.databinding.FragmentAddStockAdjustmentBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddStockAdjustmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddStockAdjustmentFragment : BaseFragment<FragmentAddStockAdjustmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddStockAdjustmentBinding =
        FragmentAddStockAdjustmentBinding::inflate

    private val viewModel: AddStockAdjustmentViewModel by viewModel()

    val myCalendar = Calendar.getInstance()
    private var locationList: ArrayList<AddStockAdjustmentViewModel.Common> = arrayListOf()
    private var location_ID = "0"
    private var id = "0"

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
            if (TextUtils.isEmpty(binding.edtAmtRecovered.text.toString().trim())) {
                binding.edtAmtRecovered.error = "Total amount recovered cannot be empty"
                binding.edtAmtRecovered.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtReason.text.toString().trim())) {
                binding.edtReason.error = "Reason cannot be empty"
                binding.edtReason.requestFocus()
                return@setOnClickListener
            }
            viewModel.addUpdateStockAdjustment(
                "Bearer " + prefs.accessToken,
                location_ID,
                binding.edtRefNO.text.toString().trim(),
                binding.edtDate.text.toString().trim(),
                binding.edtAdjustmentType.selectedItem.toString().trim(),
                "",
                "",
                binding.edtAmtRecovered.text.toString().trim(),
                binding.edtReason.text.toString().trim()
            )
        }
    }


    private fun setLocationData(dataList: ArrayList<AddStockAdjustmentViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinLocation.adapter = adapter
        binding.spinLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                location_ID = (adapter.getItem(position) as AddStockAdjustmentViewModel.Common).id
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
                        binding.edtAmtRecovered.setText("")
                        binding.edtReason.setText("")
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