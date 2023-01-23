package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.ProductAdapter
import com.alhanpos.store.databinding.FragmentAllProductBinding
import com.alhanpos.store.model.response.product.ProductListResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.GridAutofitLayoutManager
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AllProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductListingFragment : BaseFragment<FragmentAllProductBinding>(),
    ProductAdapter.ButtonClick, SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllProductBinding =
        FragmentAllProductBinding::inflate

    private val viewModel: AllProductViewModel by viewModel()
    private lateinit var adapter: ProductAdapter
    private var page = 1

    private var term = ""
    private var isLoading = false
    private var isLastPage = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            val action = ProductListingFragmentDirections.actionNavAllProductToNavAddProduct()
            findNavController().navigate(action)
        }
        setAllData()
    }

    private fun setAllData() {
        val layoutManager = GridAutofitLayoutManager(requireContext(), 400)
        adapter = ProductAdapter(arrayListOf(), this)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager = layoutManager

        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }
            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)
            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    page = page.plus(1)
                    viewModel.fetchProduct(
                        "Bearer " + prefs.accessToken, term, page.toString()
                    )
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchProduct("Bearer " + prefs.accessToken, "", page.toString())
        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        if (it.data.isNotEmpty()) {
                            if (page == 1) {
                                adapter.addData(it.data)
                            } else {
                                adapter.loadMore(it.data)
                            }
                        } else {
                            page = page.minus(1)
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

    override fun onClick(data: ProductListResponseItem) {
        val action = ProductListingFragmentDirections.actionNavAllProductToNavAddProduct(data)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        term = newText!!
        page = 1
        viewModel.fetchProduct("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}