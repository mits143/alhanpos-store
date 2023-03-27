package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentAddBrandBinding
import com.alhanpos.store.databinding.FragmentPosDetailBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddBrandViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PosDetailFragment : BaseFragment<FragmentPosDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosDetailBinding =
        FragmentPosDetailBinding::inflate

    private val viewModel: AddBrandViewModel by viewModel()

    private val args: AddBrandFragmentArgs by navArgs()

    private var id = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
    }

    private fun setObserver() {
        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
//                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
//                    binding.animationView.visibility = View.GONE

                }
                Status.ERROR -> {
//                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }
}