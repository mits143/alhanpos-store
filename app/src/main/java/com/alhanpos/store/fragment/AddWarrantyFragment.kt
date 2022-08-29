package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddWarrantyBinding

class AddWarrantyFragment : BaseFragment<FragmentAddWarrantyBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddWarrantyBinding =
        FragmentAddWarrantyBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}