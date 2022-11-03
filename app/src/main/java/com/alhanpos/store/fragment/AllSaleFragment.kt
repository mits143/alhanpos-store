package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.SellsAdapter
import com.alhanpos.store.databinding.FragmentAllSaleBinding
import com.alhanpos.store.model.response.sell.SellResponseItem
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.SellsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllSaleFragment : BaseFragment<FragmentAllSaleBinding>(), SellsAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllSaleBinding =
        FragmentAllSaleBinding::inflate

    private val viewModel: SellsViewModel by viewModel()

    private lateinit var adapter: SellsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_transfer_to_nav_add_stock_transfer)
        }
    }

    private fun setExpensesData(list: ArrayList<SellResponseItem>) {
        adapter = SellsAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchSells("Bearer " + prefs.accessToken)
        viewModel.getSellData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.animationView.visibility = View.GONE
                        setExpensesData(it)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: SellResponseItem) {
    }

    override fun onDeleteClick(data: SellResponseItem, pos: Int) {
    }
}