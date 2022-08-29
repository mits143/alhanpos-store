package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddBrandBinding

class AddStockAdjustmentFragment : BaseFragment<FragmentAddBrandBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddBrandBinding =
        FragmentAddBrandBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}