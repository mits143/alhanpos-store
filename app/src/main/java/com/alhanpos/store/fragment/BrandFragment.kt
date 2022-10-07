package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.BrandAdapter
import com.alhanpos.store.databinding.FragmentBrandBinding
import com.alhanpos.store.model.response.brand.BrandResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.BrandViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrandFragment : BaseFragment<FragmentBrandBinding>(), BrandAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrandBinding =
        FragmentBrandBinding::inflate

    private val viewModel: BrandViewModel by viewModel()

    private lateinit var adapter: BrandAdapter

    private var pos = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            val action =
                BrandFragmentDirections.actionNavBrandToNavAddBrand()
            findNavController().navigate(action)
        }
    }

    private fun setBrandData(dataList: ArrayList<BrandResponseItem>) {
        adapter = BrandAdapter(dataList, this)
        binding.rVCategory.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchBrand("Bearer " + prefs.accessToken)
        viewModel.getBrandData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        setBrandData(it)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }

        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        showToast(it)
                        adapter.removeItem(pos)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: BrandResponseItem) {
        val action =
            BrandFragmentDirections.actionNavBrandToNavAddBrand(data)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(data: BrandResponseItem, pos: Int) {
        this.pos = pos
        viewModel.deleteBrand("Bearer " + prefs.accessToken, data.id.toString())
    }
}