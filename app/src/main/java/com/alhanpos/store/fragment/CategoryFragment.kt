package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.CategoryAdapter
import com.alhanpos.store.databinding.FragmentCategoryBinding
import com.alhanpos.store.model.response.category.CategoryResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(), CategoryAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryBinding =
        FragmentCategoryBinding::inflate

    private val viewModel: CategoryViewModel by viewModel()

    private lateinit var adapter: CategoryAdapter
    private var pos = 0

    private var page = 1

    private var term = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
        binding.flAdd.setOnClickListener {
            val action = CategoryFragmentDirections.actionNavCategoryToNavAddCategory()
            findNavController().navigate(action)
        }
        setCategoryData()
    }

    private fun setCategoryData() {
        adapter = CategoryAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.nestedScrollView.height + binding.nestedScrollView
                    .scrollY)
            if (diff == 0) {
                page = page.plus(1)
                viewModel.fetchCategory(
                    "Bearer " + prefs.accessToken,
                    term,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchCategory("Bearer " + prefs.accessToken, "", "")
        viewModel.getCategoryData.observe(this) {
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
    }

    override fun onEditClick(data: CategoryResponseItem) {
        val action = CategoryFragmentDirections.actionNavCategoryToNavAddCategory(data)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(data: CategoryResponseItem, pos: Int) {
        this.pos = pos
        viewModel.deleteCategory("Bearer " + prefs.accessToken, data.id.toString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        term = newText!!
        page = 1
        viewModel.fetchCategory("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}