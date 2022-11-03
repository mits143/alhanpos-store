package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.R
import com.alhanpos.store.adapter.ExpensesAdapter
import com.alhanpos.store.databinding.FragmentExpenseBinding
import com.alhanpos.store.model.response.expenses.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.ExpensesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(), ExpensesAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentExpenseBinding =
        FragmentExpenseBinding::inflate

    private val viewModel: ExpensesViewModel by viewModel()

    private lateinit var adapter: ExpensesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.flAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_stock_transfer_to_nav_add_stock_transfer)
        }
    }

    private fun setExpensesData(list: ArrayList<Data>) {
        adapter = ExpensesAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchExpenses("Bearer " + prefs.accessToken)
        viewModel.getExpensesData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        setExpensesData(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: Data) {
    }

    override fun onDeleteClick(data: Data, pos: Int) {
    }
}