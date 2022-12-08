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
import androidx.appcompat.widget.SearchView

class StockAdjustmentFragment : BaseFragment<FragmentStockAdjustmentBinding>(),
    StockAdjustmentAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStockAdjustmentBinding =
        FragmentStockAdjustmentBinding::inflate

    private val viewModel: StockAdjustmentViewModel by viewModel()

    private lateinit var adapter: StockAdjustmentAdapter

    private var page = 1

    private var term = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_adjustment_to_nav_add_stock_adjustment)
        }
        setStockAdjustmentData()
    }

    private fun setStockAdjustmentData() {
        adapter = StockAdjustmentAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.nestedScrollView.height + binding.nestedScrollView
                    .scrollY)
            if (diff == 0) {
                page = page.plus(1)
                viewModel.fetchExpenses(
                    "Bearer " + prefs.accessToken,
                    term,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchExpenses("Bearer " + prefs.accessToken, "", "")
        viewModel.getStockAdjustmentData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        if (page == 1) {
                            adapter.addData(it.data)
                        } else {
                            adapter.loadMore(it.data)
                        }
                    }
                }
                Status.ERROR -> {
                    page = page.minus(1)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        term = newText!!
        page = 1
        viewModel.fetchExpenses("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}