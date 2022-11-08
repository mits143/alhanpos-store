package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.ProductAdapter
import com.alhanpos.store.databinding.FragmentAllProductBinding
import com.alhanpos.store.model.response.product.ProductData
import com.alhanpos.store.prefs
import com.alhanpos.store.util.GridAutofitLayoutManager
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AllProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllFragment : BaseFragment<FragmentAllProductBinding>(), ProductAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllProductBinding =
        FragmentAllProductBinding::inflate

    private val viewModel: AllProductViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            val action =
                AllFragmentDirections.actionNavAllProductToNavAddProduct()
            findNavController().navigate(action)
        }
    }

    private fun setAllData(posList: ArrayList<ProductData>) {
        val layoutManager = GridAutofitLayoutManager(requireContext(), 400)
        val adapter = ProductAdapter(posList, this)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager = layoutManager
    }

    private fun setObserver() {
        viewModel.fetchProduct("Bearer " + prefs.accessToken)
        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        setAllData(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onClick(data: ProductData) {
        val action =
            AllFragmentDirections.actionNavAllProductToNavAddProduct(data)
        findNavController().navigate(action)
    }
}