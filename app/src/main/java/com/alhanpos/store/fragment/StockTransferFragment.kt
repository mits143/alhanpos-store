package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.StockTransferAdapter
import com.alhanpos.store.databinding.FragmentStockTransferBinding
import com.alhanpos.store.model.response.stocktransfer.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.StockTransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockTransferFragment : BaseFragment<FragmentStockTransferBinding>(),
    StockTransferAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStockTransferBinding =
        FragmentStockTransferBinding::inflate

    private val viewModel: StockTransferViewModel by viewModel()

    private lateinit var adapter: StockTransferAdapter

    private var page = 1

    private var term = ""
    private var isLoading = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_transfer_to_nav_add_stock_transfer)
        }
        setStockData()
    }

    private fun setStockData() {
        adapter = StockTransferAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }
            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)
            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    page = page.plus(1)
                    viewModel.fetchStock(
                        "Bearer " + prefs.accessToken, term, page.toString()
                    )
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchStock("Bearer " + prefs.accessToken, "", page.toString())
        viewModel.getStockData.observe(this) {
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
        viewModel.fetchStock("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}