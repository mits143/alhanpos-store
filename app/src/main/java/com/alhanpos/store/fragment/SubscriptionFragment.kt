package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.adapter.MainAdapter
import com.alhanpos.store.databinding.FragmentSubscriptionBinding
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSubscriptionBinding =
        FragmentSubscriptionBinding::inflate

    private val viewModel: PosViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        adapter = MainAdapter(arrayListOf(), "TYPE_SUBSCRIPTION")
        binding.rVCategory.adapter = adapter

        viewModel.fetchData()
    }

    private fun setObserver() {
        viewModel.getData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        adapter.addData(it)
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }
    }
}