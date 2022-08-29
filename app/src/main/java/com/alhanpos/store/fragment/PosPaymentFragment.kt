package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentPosPaymentBinding

class PosPaymentFragment : BaseFragment<FragmentPosPaymentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosPaymentBinding =
        FragmentPosPaymentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}