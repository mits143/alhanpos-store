package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemStockAdjustmentBinding
import com.alhanpos.store.model.response.stockadjustment.Data

class StockAdjustmentAdapter(
    var dataList: ArrayList<Data>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<StockAdjustmentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStockAdjustmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStockAdjustmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.text = this.transactionDate
                binding.txtRefNo.text = this.refNo
                binding.txtLocation.text = this.locationName
                binding.txtAdjustment.text = this.adjustmentType
                binding.txtTotalAmt.text = this.finalTotal
                binding.txtTotalAmtRec.text = this.totalAmountRecovered
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