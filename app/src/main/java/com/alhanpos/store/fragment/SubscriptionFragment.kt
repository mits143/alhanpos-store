package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.alhanpos.store.adapter.SubscripitionAdapter
import com.alhanpos.store.databinding.FragmentSubscriptionBinding
import com.alhanpos.store.model.response.subscription.Data
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.SubscripitionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionFragment : BaseFragment<FragmentSubscriptionBinding>(),
    SubscripitionAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSubscriptionBinding =
        FragmentSubscriptionBinding::inflate

    private val viewModel: SubscripitionViewModel by viewModel()

    private lateinit var adapter: SubscripitionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.searchView.setOnQueryTextListener(this)
    }

    private fun setExpensesData(list: ArrayList<Data>) {
        adapter = SubscripitionAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        adapter.addData(list)
    }

    private fun setObserver() {
        viewModel.fetchSubscriptions("Bearer " + prefs.accessToken, "", "")
        viewModel.getSubscriptionData.observe(this) {
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.fetchSubscriptions("Bearer " + prefs.accessToken, newText!!, "")
        return false
    }
}