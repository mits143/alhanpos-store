package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentTodayBinding

class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTodayBinding =
        FragmentTodayBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}