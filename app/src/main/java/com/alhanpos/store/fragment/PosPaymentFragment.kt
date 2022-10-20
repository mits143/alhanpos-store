package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentPosPaymentBinding
import com.alhanpos.store.model.request.payment.Payment
import com.alhanpos.store.model.request.payment.PaymentRequest
import com.alhanpos.store.model.request.payment.Product
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddPosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class PosPaymentFragment : BaseFragment<FragmentPosPaymentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosPaymentBinding =
        FragmentPosPaymentBinding::inflate

    private val args: PosPaymentFragmentArgs by navArgs()

    private val viewModel: AddPosViewModel by viewModel()

    private var paymentAccountList: ArrayList<AddPosViewModel.Common> = arrayListOf()
    private var paymentMethodList: ArrayList<String> = arrayListOf()

    var accountID = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.txtTotalItems.text = args.data?.data?.size.toString()
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
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
            }
        })
        binding.txtProceed.setOnClickListener {
            setPayment()
        }
        setObserver()
    }

    private fun setPaymentAccountData(paymentAccountList: ArrayList<AddPosViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, paymentAccountList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentAccount.adapter = adapter
        binding.spinnerPaymentAccount.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    accountID = (adapter.getItem(position) as AddPosViewModel.Common).id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setPaymentMethodData(paymentMethodList: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, paymentMethodList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPaymentMethod.adapter = adapter
    }

    private fun setPayment() {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())
        val payments: ArrayList<Payment> = arrayListOf()
        val products: ArrayList<Product> = arrayListOf()
        for (i in 0 until args.data?.data?.size!!) {
            val payment = Payment(
                accountID,
                args.data?.data?.get(i)?.product_variations?.get(0)?.variations?.get(0)?.sell_price_inc_tax.toString(),
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
                ""
            )

            payments.add(payment)
        }
        for (i in 0 until args.data?.data?.size!!) {
            val product = Product(
                "1",
                "",
                "",
                "",
                "fixed",
                "",
                args.data?.data?.get(i)?.id.toString(),
                args.data?.data?.get(0)?.type!!,
                "1",
                args.data?.data?.get(i)?.quantity.toString(),
                "",
                "1",
                "",
                args.data?.data?.get(i)?.product_variations?.get(0)?.variations?.get(0)?.sell_price_inc_tax.toString(),
                args.data?.data?.get(i)?.product_variations?.get(0)?.variations?.get(0)?.sell_price_inc_tax.toString(),
                args.data?.data?.get(i)?.product_variations?.get(0)?.id.toString(),
            )

            products.add(product)
        }
        val paymentRequest = PaymentRequest(
            "",
            "",
            "",
            1,
            "",
            "",
            "",
            "",
            "",
            "percentage",
            args.total,
            0,
            "",
            "",
            "",
            1,
            "",
            "",
            "",
            payments,
            1,
            products,
            "",
            "days",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "includes",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "all",
            "",
            "final",
            "",
            "",
            "",
            "",
            currentDateandTime,
            "",
            ""
        )
        viewModel.fetchPaymentData("Bearer " + prefs.accessToken, paymentRequest)
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
                            paymentAccountList.add(
                                AddPosViewModel.Common(
                                    it.name!!, it.id.toString()
                                )
                            )
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
        viewModel.getPaymentData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        showToast(it.get("msg").asString)
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