package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.alhanpos.store.adapter.PosAdapter
import com.alhanpos.store.databinding.FragmentScannerBinding
import com.alhanpos.store.model.response.product.ProductListResponse
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.ScannerViewModel
import com.budiyev.android.codescanner.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScannerFragment : BaseFragment<FragmentScannerBinding>(), PosAdapter.ButtonClick {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScannerBinding =
        FragmentScannerBinding::inflate

    private val viewModel: ScannerViewModel by viewModel()

    private var productDataList: ArrayList<ProductListResponse.ProductListResponseItem> =
        arrayListOf()
    lateinit var adapter: PosAdapter

    private lateinit var codeScanner: CodeScanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScanner()
        setObserver()
    }

    private fun initScanner() {
        codeScanner = CodeScanner(requireContext(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback {
            lifecycleScope.launch(Dispatchers.Main) {
//                prefs.sku = it.text
//                requireActivity().onBackPressedDispatcher.onBackPressed()
                viewModel.fetchProduct("Bearer " + prefs.accessToken!!, "", it.text)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        codeScanner.startPreview()

        binding.btnDone.setOnClickListener {
            prefs.saveArrayList(productDataList)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setObserver() {
        viewModel.getProductData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    if (it.data != null) {
                        it.data.let {
                            binding.animationView.visibility = View.GONE
//                            productDataList.clear()
                            productDataList.addAll(it.data)
                            setPosData(productDataList)
                        }
                    } else {
                        showToast("No data available")
                    }
                }
                Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    private fun setPosData(posList: ArrayList<ProductListResponse.ProductListResponseItem>) {
        adapter = PosAdapter(posList, this)
        binding.rvProducts.adapter = adapter
        codeScanner.releaseResources()
        codeScanner.startPreview()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onClick(data: ProductListResponse.ProductListResponseItem, position: Int) {
    }

}