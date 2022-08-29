package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.MainAdapter
import com.alhanpos.store.databinding.FragmentManageUsersBinding
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageUserFragment : BaseFragment<FragmentManageUsersBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentManageUsersBinding =
        FragmentManageUsersBinding::inflate

    private val viewModel: PosViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        adapter = MainAdapter(arrayListOf(), "TYPE_MANAGE_USERS")
        binding.rVCategory.adapter = adapter
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_purchase_order_to_nav_add_purchase_order)
        }

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
                }
            }
        }
    }
}