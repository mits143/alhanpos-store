package com.alhanpos.store.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.alhanpos.store.databinding.FragmentScannerBinding
import com.alhanpos.store.prefs
import com.budiyev.android.codescanner.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScannerFragment : BaseFragment<FragmentScannerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScannerBinding =
        FragmentScannerBinding::inflate

    private lateinit var codeScanner: CodeScanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScanner()
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
                prefs.sku = it.text
                requireActivity().onBackPressedDispatcher.onBackPressed()
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
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}