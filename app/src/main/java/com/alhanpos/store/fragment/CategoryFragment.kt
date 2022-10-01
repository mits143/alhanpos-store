package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.CategoryAdapter
import com.alhanpos.store.databinding.FragmentCategoryBinding
import com.alhanpos.store.model.response.category.CategoryResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(), CategoryAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryBinding =
        FragmentCategoryBinding::inflate

    private val viewModel: CategoryViewModel by viewModel()

    private lateinit var adapter: CategoryAdapter
    private var pos = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            val action =
                CategoryFragmentDirections.actionNavCategoryToNavAddCategory()
            findNavController().navigate(action)
        }
    }

    private fun setCategoryData(dataList: ArrayList<CategoryResponseItem>) {
        adapter = CategoryAdapter(dataList, this)
        binding.rVCategory.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchCategory("Bearer " + prefs.accessToken)
        viewModel.getCategoryData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        setCategoryData(it)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }

        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        showToast(it)
                        adapter.removeItem(pos)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: CategoryResponseItem) {
        val action =
            CategoryFragmentDirections.actionNavCategoryToNavAddCategory(data)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(data: CategoryResponseItem, pos: Int) {
        this.pos = pos
        viewModel.deleteCategory("Bearer " + prefs.accessToken, data.id.toString())
    }
}