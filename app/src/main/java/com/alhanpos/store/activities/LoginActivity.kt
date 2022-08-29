package com.alhanpos.store.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.alhanpos.store.databinding.ActivityLoginBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding =
        ActivityLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        setupObserver()

        binding.txtLogin.setOnClickListener {
            loginValidation()
        }
        binding.txtForgotPwd.setOnClickListener {

        }
    }

    private fun loginValidation() {
        if (TextUtils.isEmpty(binding.edtEmail.text.toString().trim())) {
            binding.edtEmail.requestFocus()
            binding.edtEmail.error = "Username cannot be empty"
            return
        }
        if (TextUtils.isEmpty(binding.edtPwd.text.toString().trim())) {
            binding.edtPwd.requestFocus()
            binding.edtPwd.error = "Password cannot be empty"
            return
        }
        viewModel.fetchUsers(
            binding.edtEmail.text.toString().trim(),
            binding.edtPwd.text.toString().trim()
        )
    }

    private fun setupObserver() {
        viewModel.getUser.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.animationView.visibility = View.GONE
                        prefs.accessToken = it.access_token
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
//                    showToast(it.message.toString())
                }
            }
        })
    }
}