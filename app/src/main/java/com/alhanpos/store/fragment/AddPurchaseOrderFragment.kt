package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddPurchaseOrderBinding

class AddPurchaseOrderFragment : BaseFragment<FragmentAddPurchaseOrderBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPurchaseOrderBinding =
        FragmentAddPurchaseOrderBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}