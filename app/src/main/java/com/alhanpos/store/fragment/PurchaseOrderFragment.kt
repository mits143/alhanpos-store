package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.MainAdapter
import com.alhanpos.store.databinding.FragmentPurchaseOrderBinding
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseOrderFragment : BaseFragment<FragmentPurchaseOrderBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchaseOrderBinding =
        FragmentPurchaseOrderBinding::inflate

    private val viewModel: PosViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        adapter = MainAdapter(arrayListOf(), "TYPE_PURCHASE_ORDER")
        binding.rVCategory.adapter = adapter
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_purchase_order_to_nav_add_purchase_order)
        }

        viewModel.fetchData()
    }

    private fun setObserver() {
        viewModel.getData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        adapter.addData(it)
                    }
                }
                Status.ERROR -> {
                }
            }
        }
    }
}