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

    private var page = 1

    private var term = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            val action =
                BrandFragmentDirections.actionNavBrandToNavAddBrand()
            findNavController().navigate(action)
        }

        setBrandData()
    }

    private fun setBrandData() {
        adapter = BrandAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.nestedScrollView.height + binding.nestedScrollView
                    .scrollY)
            if (diff == 0) {
                page = page.plus(1)
                viewModel.fetchBrand(
                    "Bearer " + prefs.accessToken,
                    term,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchBrand("Bearer " + prefs.accessToken, term, page.toString())
        viewModel.getBrandData.observe(this) {
            it.getContentIfNotHandled()?.let { //
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
        term = newText!!
        viewModel.fetchBrand("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}