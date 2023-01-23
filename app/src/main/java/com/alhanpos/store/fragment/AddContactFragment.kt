package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.databinding.FragmentAddContactBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddContactViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddContactFragment : BaseFragment<FragmentAddContactBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddContactBinding =
        FragmentAddContactBinding::inflate

    private val viewModel: AddContactViewModel by viewModel()

    private val args: AddContactFragmentArgs by navArgs()

    private var id = "0"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        binding.edtName.setText(args.data?.name)
        binding.edtID.setText(args.data?.contact_id)
        binding.edtMobile.setText(args.data?.mobile)
        binding.edtEmail.setText(args.data?.email)
        binding.edtAdvanceBal.setText(args.data?.balance)
        binding.edtTotalDue.setText(args.data?.total_due)

        binding.txtProceed.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtName.text.toString().trim())) {
                binding.edtName.error = "Customer name cannot be empty"
                binding.edtName.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtID.text.toString().trim())) {
                binding.edtID.error = "ID cannot be empty"
                binding.edtID.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtMobile.text.toString().trim())) {
                binding.edtMobile.error = "Mobile cannot be empty"
                binding.edtMobile.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtEmail.text.toString().trim())) {
                binding.edtEmail.error = "Email cannot be empty"
                binding.edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.edtAdvanceBal.text.toString().trim())) {
                binding.edtAdvanceBal.error = "Advance Balance cannot be empty"
                binding.edtAdvanceBal.requestFocus()
                return@setOnClickListener
            }
//            if (TextUtils.isEmpty(binding.edtTotalDue.text.toString().trim())) {
//                binding.edtTotalDue.error = "Total Due cannot be empty"
//                binding.edtTotalDue.requestFocus()
//                return@setOnClickListener
//            }
            viewModel.addUpdateCustomer(
                "Bearer " + prefs.accessToken,
                binding.edtName.text.toString().trim(),
                binding.edtEmail.text.toString().trim(),
                binding.edtMobile.text.toString().trim(),
                binding.edtAdvanceBal.text.toString().trim(),
                args.data?.id.toString(),
                "0"
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
                        binding.edtName.setText("")
                        binding.edtID.setText("")
                        binding.edtMobile.setText("")
                        binding.edtEmail.setText("")
                        binding.edtAdvanceBal.setText("")
                        binding.edtTotalDue.setText("")
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