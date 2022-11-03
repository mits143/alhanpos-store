package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemSaleBinding
import com.alhanpos.store.model.response.sell.SellResponseItem

class SellsAdapter(
    var dataList: ArrayList<SellResponseItem>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<SellsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSaleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.text = this.saleDate
                binding.txtInvoice.text = this.invoiceNo
                binding.txtCustName.text = this.name
                binding.txtContactNo.text = this.mobile
                binding.txtLocation.text = this.businessLocation
                binding.txtPaymentStatus.text = this.paymentStatus
                binding.txtPaymentMethod.text = this.transactionDate
                binding.txtTotalAmt.text = this.finalTotal
                binding.txtTotalPaid.text = this.totalPaid
//                binding.txtSellDue.text = this.due
//                binding.txtSellReturnDate.text = this.retu
                binding.txtShippingStatus.text = this.shippingStatus
                binding.txtTotalItems.text = this.totalItems
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<SellResponseItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    interface ButtonClick {
        fun onEditClick(data: SellResponseItem)
        fun onDeleteClick(data: SellResponseItem, pos: Int)
    }
}