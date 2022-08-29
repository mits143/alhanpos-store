package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddContactBinding

class AddContactFragment : BaseFragment<FragmentAddContactBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddContactBinding =
        FragmentAddContactBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}