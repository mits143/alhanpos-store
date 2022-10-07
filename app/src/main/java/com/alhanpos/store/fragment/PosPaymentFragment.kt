package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentPosPaymentBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddPosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PosPaymentFragment : BaseFragment<FragmentPosPaymentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosPaymentBinding =
        FragmentPosPaymentBinding::inflate

    private val args: PosPaymentFragmentArgs by navArgs()

    private val viewModel: AddPosViewModel by viewModel()

    private var paymentAccountList: ArrayList<String> = arrayListOf()
    private var paymentMethodList: ArrayList<String> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.txtTotalItems.text = args.totalItems
        binding.txtTotal.text = args.total

        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var value = 0f
                if (s.toString().isEmpty()) {
                    value = 0f
                    binding.txtBalance.text = "0.0"
                    binding.txtChangeReturn.text = "0.0"
                } else {
                    value = s.toString().toFloat()
                    binding.txtTotalPaying.text = value.toString()
                    if (value > binding.txtTotal.text.toString().toFloat()) {
                        binding.txtChangeReturn.text =
                            (value - binding.txtTotal.text.toString().toFloat()).toString()
                        binding.txtBalance.text = "0.0"
                    } else {
                        binding.txtBalance.text =
                            (binding.txtTotal.text.toString().toFloat() - value).toString()
                        binding.txtChangeReturn.text = "0.0"
                    }

                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
        setObserver()
    }

    private fun setPaymentAccountData(locationList: ArrayList<String>) {
//        val adapter =
//            ArrayAdapter(requireContext(), R.layout.spinner_item, locationList)
//        binding.spinnerPaymentAccount.adapter = adapter

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, locationList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentAccount.adapter = adapter
    }

    private fun setPaymentMethodData(contactList: ArrayList<String>) {
//        val adapter =
//            ArrayAdapter(requireContext(), R.layout.spinner_item, contactList)
//        binding.spinnerPaymentMethod.adapter = adapter

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, contactList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentMethod.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchPaymentAccountData("Bearer " + prefs.accessToken!!)
        viewModel.getPaymentAccountData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        paymentAccountList.clear()
                        it.data.forEach {
                            paymentAccountList.add(it.name)
                        }
                        setPaymentAccountData(paymentAccountList)
                    }
                    viewModel.fetchPaymentMethodData("Bearer " + prefs.accessToken!!)
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
    }
}