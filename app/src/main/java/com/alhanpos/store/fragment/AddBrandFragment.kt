package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.databinding.FragmentAddBrandBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddBrandViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBrandFragment : BaseFragment<FragmentAddBrandBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddBrandBinding =
        FragmentAddBrandBinding::inflate

    private val viewModel: AddBrandViewModel by viewModel()

    private val args: AddBrandFragmentArgs by navArgs()

    private var id = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()


        binding.edtBrandName.setText(args.data?.name)
        binding.edtDesc.setText(args.data?.description)
        id = args.data?.id.toString()

        binding.txtProceed.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtBrandName.text.toString().trim())) {
                binding.edtBrandName.error = "Category name cannot be empty"
                binding.edtBrandName.requestFocus()
                return@setOnClickListener
            }
            viewModel.addUpdateBrand(
                "Bearer " + prefs.accessToken,
                id,
                binding.edtBrandName.text.toString().trim(),
                binding.edtDesc.text.toString().trim(),
                if (binding.checkbox.isChecked) "1" else "0"
            )
        }
    }

    private fun setObserver() {
        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        showToast(it)
                        binding.edtBrandName.setText("")
                        binding.edtDesc.setText("")
                        binding.checkbox.isChecked = false
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }
}