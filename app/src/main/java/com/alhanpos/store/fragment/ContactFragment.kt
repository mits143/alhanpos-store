package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.adapter.ContactAdapter
import com.alhanpos.store.databinding.FragmentContactBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactFragment : BaseFragment<FragmentContactBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactBinding =
        FragmentContactBinding::inflate

    private val viewModel: ContactViewModel by viewModel()

    private lateinit var adapter: ContactAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        setContactData()
    }

    private fun setContactData() {
        adapter = ContactAdapter(arrayListOf())
        binding.rVCategory.adapter = adapter
    }

    private fun setObserver() {
        viewModel.fetchContact("Bearer " + prefs.accessToken)
        viewModel.getContactData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        adapter.addData(it.data)
                    }
                }
                Status.ERROR -> {
                }
            }
        }
    }
}