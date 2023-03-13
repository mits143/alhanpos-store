package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.PurchaseOrderAdapter
import com.alhanpos.store.databinding.FragmentPurchaseOrderBinding
import com.alhanpos.store.model.response.purchase.PurchaseResponse.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PurchaseOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseOrderFragment : BaseFragment<FragmentPurchaseOrderBinding>(),
    PurchaseOrderAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchaseOrderBinding =
        FragmentPurchaseOrderBinding::inflate

    private val viewModel: PurchaseOrderViewModel by viewModel()

    private lateinit var adapter: PurchaseOrderAdapter

    private var page = 1

    private var term = ""
    private var isLoading = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_purchase_order_to_nav_add_purchase_order)
        }
        setPurchaseOrderData()
    }

    private fun setPurchaseOrderData() {
        adapter = PurchaseOrderAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }
            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)
            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    page = page.plus(1)
                    viewModel.fetchPurchase(
                        "Bearer " + prefs.accessToken, term, page.toString()
                    )
                }

            }
        }
    }

    private fun setObserver() {
        viewModel.fetchPurchase("Bearer " + prefs.accessToken, "", page.toString())
        viewModel.getPurchaseData.observe(this) {
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
        viewModel.fetchPurchaseOrder("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}