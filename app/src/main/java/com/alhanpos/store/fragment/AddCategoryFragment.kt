package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alhanpos.store.databinding.FragmentAddCategoryBinding

class AddCategoryFragment : BaseFragment<FragmentAddCategoryBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddCategoryBinding =
        FragmentAddCategoryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}