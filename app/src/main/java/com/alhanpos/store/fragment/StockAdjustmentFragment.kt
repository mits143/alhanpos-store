package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.StockAdjustmentAdapter
import com.alhanpos.store.databinding.FragmentStockAdjustmentBinding
import com.alhanpos.store.model.response.stockadjustment.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.StockAdjustmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockAdjustmentFragment : BaseFragment<FragmentStockAdjustmentBinding>(),
    StockAdjustmentAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStockAdjustmentBinding =
        FragmentStockAdjustmentBinding::inflate

    private val viewModel: StockAdjustmentViewModel by viewModel()

    private lateinit var adapter: StockAdjustmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_adjustment_to_nav_add_stock_adjustment)
        }
    }

    private fun setStockAdjustmentData(list: ArrayList<Data>) {
        adapter = StockAdjustmentAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchExpenses("Bearer " + prefs.accessToken)
        viewModel.getStockAdjustmentData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        setStockAdjustmentData(it.data)
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