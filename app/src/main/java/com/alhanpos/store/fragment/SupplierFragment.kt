package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.SupplierAdapter
import com.alhanpos.store.databinding.FragmentContactBinding
import com.alhanpos.store.model.response.contact.ContactData
import com.alhanpos.store.prefs
import com.alhanpos.store.util.GridAutofitLayoutManager
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupplierFragment : BaseFragment<FragmentContactBinding>(), SupplierAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactBinding =
        FragmentContactBinding::inflate

    private val viewModel: ContactViewModel by viewModel()

    private lateinit var adapter: SupplierAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        setContactData()

        binding.flAdd.setOnClickListener {
            val action =
                SupplierFragmentDirections.actionNavSupplierToNavAddSupplier()
            findNavController().navigate(action)
        }
    }

    private fun setContactData() {
        val layoutManager = GridAutofitLayoutManager(requireContext(), 400)
        adapter = SupplierAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        binding.rVCategory.layoutManager = layoutManager
    }

    private fun setObserver() {

        viewModel.fetchSupplier("Bearer " + prefs.accessToken)
        viewModel.getContactData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        adapter.addData(it.data)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onEditClick(data: ContactData) {
        val action =
            SupplierFragmentDirections.actionNavSupplierToNavAddSupplier(data)
        findNavController().navigate(action)
    }
}