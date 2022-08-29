package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddBrandBinding
import com.alhanpos.store.databinding.FragmentAddStockTransferBinding

class AddStockTransferFragment : BaseFragment<FragmentAddStockTransferBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddStockTransferBinding =
        FragmentAddStockTransferBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}