package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
    ProductAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllProductBinding =
        FragmentAllProductBinding::inflate

    private val viewModel: AllProductViewModel by viewModel()
    private lateinit var adapter: ProductAdapter
    private var page = 1

    private var term = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            val action =
                ProductListingFragmentDirections.actionNavAllProductToNavAddProduct()
            findNavController().navigate(action)
        }
        setAllData()
    }

    private fun setAllData() {
        val layoutManager = GridAutofitLayoutManager(requireContext(), 400)
        adapter = ProductAdapter(arrayListOf(), this)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager = layoutManager

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.nestedScrollView.height + binding.nestedScrollView
                    .scrollY)
            if (diff == 0) {
                page = page.plus(1)
                viewModel.fetchProduct(
                    "Bearer " + prefs.accessToken,
                    term,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchProduct("Bearer " + prefs.accessToken, "", "")
        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        if (page == 1) {
                            adapter.addData(it)
                        } else {
                            adapter.loadMore(it)
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
        val action =
            ProductListingFragmentDirections.actionNavAllProductToNavAddProduct(data)
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