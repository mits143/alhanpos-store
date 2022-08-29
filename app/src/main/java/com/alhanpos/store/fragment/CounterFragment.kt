package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentCounterBinding

class CounterFragment : BaseFragment<FragmentCounterBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCounterBinding =
        FragmentCounterBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}