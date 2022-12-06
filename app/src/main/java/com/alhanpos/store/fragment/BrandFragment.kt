package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.BrandAdapter
import com.alhanpos.store.databinding.FragmentBrandBinding
import com.alhanpos.store.model.response.brand.BrandResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.BrandViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrandFragment : BaseFragment<FragmentBrandBinding>(), BrandAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrandBinding =
        FragmentBrandBinding::inflate

    private val viewModel: BrandViewModel by viewModel()

    private lateinit var adapter: BrandAdapter

    private var position = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
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
        viewModel.fetchBrand("Bearer " + prefs.accessToken, "")
        viewModel.getBrandData.observe(this) {
            it.getContentIfNotHandled()?.let { //
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
        }

        viewModel.getMsg.observe(this) {
            it.getContentIfNotHandled()?.let { //
                when (it.status) {
                    Status.LOADING -> {
                        binding.animationView.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.animationView.visibility = View.GONE
                        it.data?.let {
                            showToast(it)
                            adapter.removeItem(position)
                        }
                    }
                    Status.ERROR -> {
                        binding.animationView.visibility = View.GONE
                        showToast(it.message)
                    }
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
        position = pos
        viewModel.deleteBrand("Bearer " + prefs.accessToken, data.id.toString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.fetchBrand("Bearer " + prefs.accessToken, newText!!)
        return false
    }
}