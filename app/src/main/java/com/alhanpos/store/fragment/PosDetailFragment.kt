package com.alhanpos.store.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alhanpos.store.R
import com.alhanpos.store.adapter.PosDetailAdapter
import com.alhanpos.store.databinding.FragmentPosDetailBinding
import com.alhanpos.store.model.response.DetailResponse
import com.alhanpos.store.prefs
import com.alhanpos.store.util.Status
import com.alhanpos.store.viewmodel.PosDetailViewModel
import com.google.zxing.WriterException
import org.koin.androidx.viewmodel.ext.android.viewModel


class PosDetailFragment : BaseFragment<FragmentPosDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPosDetailBinding =
        FragmentPosDetailBinding::inflate

    private val viewModel: PosDetailViewModel by viewModel()

    private val args: PosDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObserver()

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.nav_home)
                }
            })
    }

    private fun showDetails(it: DetailResponse) {
        binding.txtLocation.text = it.transaction.locationName
        binding.txtAddress.text =
            it.transaction.address.toString() + "\n" + it.transaction.contact.toString() + ", " + it.transaction.website.toString()
        binding.txtInvoiceValue.text = it.transaction.invoiceNo
        binding.txtDateValue.text = it.transaction.transactionDate
        binding.txtSellerValue.text = it.transaction.salesPerson
        binding.txtCustomerValue.text = it.transaction.customerName
        binding.txtMobileValue.text = it.transaction.customerMobile
        binding.txtClientValue.text = it.transaction.clientId
        binding.txtSubTotalValue.text = it.transaction.subtotal.toString()
        binding.txtTotalValue.text = it.transaction.total
//                    binding.txtCashValue.text =
//                        it.transaction?.additionalExpenseKey1
        binding.txtTotalPaidValue.text = it.transaction.totalPaid

        val adapter = PosDetailAdapter(it.transaction.lines)
        binding.rvItems.adapter = adapter

        val qrgEncoder = QRGEncoder(
            it.transaction.total.toString(), null, QRGContents.Type.TEXT, 100
        )
        qrgEncoder.colorBlack = Color.WHITE
        qrgEncoder.colorWhite = Color.BLACK
        try {
            val bitmap = qrgEncoder.bitmap
            binding.imgQR.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("TAG", e.toString())
        }
    }

    private fun setObserver() {
        viewModel.posDetail("Bearer " + prefs.accessToken, args.transactionID)
        viewModel.getMsg.observe(this) {
            when (it.status) {
                Status.LOADING -> {
//                    binding.animationView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
//                    binding.animationView.visibility = View.GONE
                    showDetails(it.data!!)
                }
                Status.ERROR -> {
//                    binding.animationView.visibility = View.GONE
                    showToast(it.message)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                showToast("Hello")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}