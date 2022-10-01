package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.adapter.ProductAdapter
import com.alhanpos.store.databinding.FragmentAllProductBinding
import com.alhanpos.store.model.response.product.ProductData
import com.alhanpos.store.prefs
import com.alhanpos.store.util.GridAutofitLayoutManager
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AllProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllFragment : BaseFragment<FragmentAllProductBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllProductBinding =
        FragmentAllProductBinding::inflate

    private val viewModel: AllProductViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    private fun setAllData(posList: ArrayList<ProductData>) {
        val layoutManager = GridAutofitLayoutManager(requireContext(), 325)
        val adapter = ProductAdapter(posList)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager = layoutManager
    }

    private fun setObserver() {
        viewModel.fetchProduct("Bearer " + prefs.accessToken)
        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        setAllData(it.data)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }
    }
}