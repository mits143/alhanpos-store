package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentItemsBinding

class ItemsFragment : BaseFragment<FragmentItemsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemsBinding =
        FragmentItemsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}