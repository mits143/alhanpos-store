package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.PurchaseOrderAdapter
import com.alhanpos.store.databinding.FragmentPurchaseOrderBinding
import com.alhanpos.store.model.response.purchaseorder.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PurchaseOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseOrderFragment : BaseFragment<FragmentPurchaseOrderBinding>(),
    PurchaseOrderAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchaseOrderBinding =
        FragmentPurchaseOrderBinding::inflate

    private val viewModel: PurchaseOrderViewModel by viewModel()

    private lateinit var adapter: PurchaseOrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_transfer_to_nav_add_stock_transfer)
        }
    }

    private fun setPurchaseOrderData(list: ArrayList<Data>) {
        adapter = PurchaseOrderAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchPurchaseOrder("Bearer " + prefs.accessToken)
        viewModel.getPurchaseOrderData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        setPurchaseOrderData(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: Data) {
    }

    override fun onDeleteClick(data: Data, pos: Int) {
    }
}