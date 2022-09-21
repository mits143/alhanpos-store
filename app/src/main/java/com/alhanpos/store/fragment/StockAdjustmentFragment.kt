package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentStockAdjustmentBinding

class StockAdjustmentFragment : BaseFragment<FragmentStockAdjustmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStockAdjustmentBinding =
        FragmentStockAdjustmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}