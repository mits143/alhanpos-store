package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemPurchaseOrderBinding
import com.alhanpos.store.model.response.purchaseorder.Data

class PurchaseOrderAdapter(
    var dataList: ArrayList<Data>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<PurchaseOrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPurchaseOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPurchaseOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.text = this.transactionDate
                binding.txtRefNo.text = this.refNo
                binding.txtLocation.text = this.locationName
                binding.txtSupplier.text = this.supplierBusinessName
//                binding.txtAction.text = this.a
                binding.txtStatus.text = this.status
                binding.txtQuantityRemaining.text = this.poQtyRemaining
                binding.txtShippingStatus.text = this.shippingStatus
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<Data>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<Data>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    interface ButtonClick {
        fun onEditClick(data: Data)
        fun onDeleteClick(data: Data, pos: Int)
    }
}