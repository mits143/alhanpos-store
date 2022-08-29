package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddBrandBinding
import com.alhanpos.store.databinding.FragmentAddSaleBinding

class AddSaleFragment : BaseFragment<FragmentAddSaleBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddSaleBinding =
        FragmentAddSaleBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}