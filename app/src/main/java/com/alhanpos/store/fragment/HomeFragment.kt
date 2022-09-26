package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentHomeBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    private fun setObserver() {
        viewModel.fetchHomeData("Bearer " + prefs.accessToken)
        viewModel.getHomeData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.txtTotalPurchase?.text = it.total_purchase.toString()
                        binding.txtTotalPurchaseReturn?.text = it.total_purchase_return.toString()
                        binding.txtPurchaseDue?.text = it.purchase_due.toString()
                        binding.txtTotalSales?.text = it.total_sell.toString()
                        binding.txtTotalSellReturn?.text = it.total_sell_return.toString()
                        binding.txtInvoiceDue?.text = it.invoice_due.toString()
                        binding.txtExpense?.text = it.total_expense.toString()
                    }
                }
                Status.ERROR -> {
                    showToast(it.message!!)
                }
            }
        }
    }
}