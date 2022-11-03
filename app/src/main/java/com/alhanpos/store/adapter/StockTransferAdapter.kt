package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemStockTransferBinding
import com.alhanpos.store.model.response.stocktransfer.Data

class StockTransferAdapter(
    var dataList: ArrayList<Data>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<StockTransferAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStockTransferBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStockTransferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.text = this.transactionDate
//                binding.txtCode.text = this.shortCode
                binding.txtRefNo.text = this.refNo
                binding.txtLocationFrom.text = this.locationFrom
                binding.txtLocationTO.text = this.locationTo
                binding.txtStatus.text = this.status
                binding.txtShippingCharges.text = this.shippingCharges
                binding.txtTotalAmt.text = this.finalTotal
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