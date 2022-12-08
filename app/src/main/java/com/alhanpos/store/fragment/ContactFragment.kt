package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.alhanpos.store.adapter.ContactAdapter
import com.alhanpos.store.databinding.FragmentContactBinding
import com.alhanpos.store.model.response.contact.ContactData
import com.alhanpos.store.prefs
import com.alhanpos.store.util.GridAutofitLayoutManager
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactFragment : BaseFragment<FragmentContactBinding>(), ContactAdapter.ButtonClick,
    SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactBinding =
        FragmentContactBinding::inflate

    private val viewModel: ContactViewModel by viewModel()

    private lateinit var adapter: ContactAdapter

    private var page = 1

    private var term = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        setContactData()
        binding.searchView.setOnQueryTextListener(this)

        binding.flAdd.setOnClickListener {
            val action =
                ContactFragmentDirections.actionNavContactToNavAddContact()
            findNavController().navigate(action)
        }
    }

    private fun setContactData() {
//        adapter = ContactAdapter(arrayListOf())
        val layoutManager = GridAutofitLayoutManager(requireContext(), 400)
        adapter = ContactAdapter(arrayListOf(), this)
        binding.rVCategory.adapter = adapter
        binding.rVCategory.layoutManager = layoutManager

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val view =
                binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1) as View
            val diff: Int =
                view.bottom - (binding.nestedScrollView.height + binding.nestedScrollView
                    .scrollY)
            if (diff == 0) {
                page = page.plus(1)
                viewModel.fetchContact(
                    "Bearer " + prefs.accessToken,
                    term,
                    page.toString()
                )
            }
        }
    }

    private fun setObserver() {
        viewModel.fetchContact("Bearer " + prefs.accessToken, "", "")
        viewModel.getContactData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        if (page == 1) {
                            adapter.addData(it.data)
                        } else {
                            adapter.loadMore(it.data)
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

    override fun onEditClick(data: ContactData) {
        val action =
            ContactFragmentDirections.actionNavContactToNavAddContact(data)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        term = newText!!
        page = 1
        viewModel.fetchContact("Bearer " + prefs.accessToken, term, page.toString())
        return false
    }
}