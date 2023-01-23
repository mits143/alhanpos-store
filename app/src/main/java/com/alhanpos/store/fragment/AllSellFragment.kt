package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.SellsAdapter
import com.alhanpos.store.databinding.FragmentAllSellBinding
import com.alhanpos.store.model.response.sell.SellResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.SellsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllSellFragment : BaseFragment<FragmentAllSellBinding>(), SellsAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllSellBinding =
        FragmentAllSellBinding::inflate

    private val viewModel: SellsViewModel by viewModel()

    private lateinit var adapter: SellsAdapter

    private var page = 1

    private var term = ""
    private var isLoading = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_all_sale_to_nav_pos)
        }
        setExpensesData()
    }

    private fun setExpensesData() {
        adapter = SellsAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }
            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)
            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    page = page.plus(1)
                    viewModel.fetchSells(
                        "Bearer " + prefs.accessToken, term, page.toString()
                    )
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchSells("Bearer " + prefs.accessToken, "", page.toString())
        viewModel.getSellData.observe(this) {
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

    override fun onEditClick(data: SellResponseItem) {
    }

    override fun onDeleteClick(data: SellResponseItem, pos: Int) {
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        term = newText!!
        page = 1
        viewModel.fetchSells("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}