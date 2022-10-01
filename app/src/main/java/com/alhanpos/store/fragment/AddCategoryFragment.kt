package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.databinding.FragmentAddCategoryBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddCategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCategoryFragment : BaseFragment<FragmentAddCategoryBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddCategoryBinding =
        FragmentAddCategoryBinding::inflate

    private val viewModel: AddCategoryViewModel by viewModel()

    private val args: AddCategoryFragmentArgs by navArgs()

    private var id = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()


        binding.edtCategoryName.setText(args.data?.name)
        binding.edtCategoryCode.setText(args.data?.shortCode)
        binding.edtCategoryDesc.setText(args.data?.description)
        id = args.data?.id.toString()

        binding.txtProceed.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtCategoryName.text.toString().trim())) {
                binding.edtCategoryName.error = "Category name cannot be empty"
                binding.edtCategoryName.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtCategoryCode.text.toString().trim())) {
                binding.edtCategoryCode.error = "Category code cannot be empty"
                binding.edtCategoryCode.requestFocus()
                return@setOnClickListener
            }
            viewModel.addUpdateCategory(
                "Bearer " + prefs.accessToken,
                id,
                binding.edtCategoryName.text.toString().trim(),
                binding.edtCategoryCode.text.toString().trim(),
                "product",
                binding.edtCategoryDesc.text.toString().trim(),
                if (binding.checkbox.isChecked) "1" else "0"
            )
        }
    }

    private fun setObserver() {
        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        showToast(it)
                        binding.edtCategoryName.setText("")
                        binding.edtCategoryCode.setText("")
                        binding.edtCategoryDesc.setText("")
                        binding.checkbox.isChecked = false
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
            }
        }
    }
}