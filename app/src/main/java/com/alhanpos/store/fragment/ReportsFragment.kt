package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentReportsBinding

class ReportsFragment : BaseFragment<FragmentReportsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReportsBinding =
        FragmentReportsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}