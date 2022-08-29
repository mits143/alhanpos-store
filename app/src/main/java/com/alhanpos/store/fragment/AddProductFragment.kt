package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddProductBinding

class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddProductBinding =
        FragmentAddProductBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}