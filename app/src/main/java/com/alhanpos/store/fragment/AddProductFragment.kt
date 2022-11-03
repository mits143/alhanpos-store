package com.alhanpos.store.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alhanpos.store.R
import com.alhanpos.store.databinding.FragmentAddProductBinding
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.AddProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddProductBinding =
        FragmentAddProductBinding::inflate

    private val viewModel: AddProductViewModel by viewModel()

    var unitList: ArrayList<AddProductViewModel.Common> = ArrayList()
    var categoryList: ArrayList<AddProductViewModel.Common> = ArrayList()
    var brandList: ArrayList<AddProductViewModel.Common> = ArrayList()

    var categoryID = ""
    var brandID = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()
        binding.txtProceed.setOnClickListener {
            addProduct()
        }
    }

    private fun setCategoryData(dataList: ArrayList<AddProductViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinCategory.adapter = adapter
        binding.spinCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoryID = (adapter.getItem(position) as AddProductViewModel.Common).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setBrandData(dataList: ArrayList<AddProductViewModel.Common>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinBrand.adapter = adapter

        binding.spinBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                brandID = (adapter.getItem(position) as AddProductViewModel.Common).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun addProduct() {
        if (TextUtils.isEmpty(binding.edtProductName.text.toString().trim())) {
            binding.edtProductName.error = "Product name cannot be empty"
            binding.edtProductName.requestFocus()
            return
        }
//        if (TextUtils.isEmpty(binding.edtPrice.text.toString().trim())) {
//            binding.edtPrice.error = "Price cannot be empty"
//            binding.edtPrice.requestFocus()
//            return
//        }
        viewModel.addUpdateProduct(
            "Bearer " + prefs.accessToken,
            binding.edtProductName.text.toString().trim(),
            categoryID,
            brandID,
            "0",
//            binding.edtPrice.text.toString().trim(),
        ""
        )
    }

    private fun setObserver() {
        viewModel.fetchCategory("Bearer " + prefs.accessToken)

        viewModel.getCategoryData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        it.forEach {
                            categoryList.add(
                                AddProductViewModel.Common(
                                    it.name!!,
                                    it.id.toString()
                                )
                            )
                        }
                        setCategoryData(categoryList)
                    }
                    viewModel.fetchBrand("Bearer " + prefs.accessToken)
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }

        viewModel.getBrandData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        it.forEach {
                            brandList.add(
                                AddProductViewModel.Common(
                                    it.name!!,
                                    it.id.toString()
                                )
                            )
                        }
                        setBrandData(brandList)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }

        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.animationView.visibility = View.GONE
                    it.data?.let {
                        showToast(it)
                        binding.edtProductName.setText("")
//                        binding.edtPrice.setText("")
                        binding.spinCategory.setSelection(0)
                        binding.spinBrand.setSelection(0)
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }
}