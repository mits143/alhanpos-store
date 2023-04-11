package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemPosDetailBinding
import com.alhanpos.store.model.response.DetailResponse

class PosDetailAdapter(
    var dataList: ArrayList<DetailResponse.Transaction.Line>,
) : RecyclerView.Adapter<PosDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPosDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPosDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtName.text = this.name
                binding.txtPrice.text = this.baseUnitMultiplier.toString() + "*" + this.unitPrice
                binding.txtQty.text = this.unitPriceUf
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<DetailResponse.Transaction.Line>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<DetailResponse.Transaction.Line>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    interface ButtonClick {
        fun onEditClick(data: DetailResponse.Transaction.Line)
        fun onDeleteClick(data: DetailResponse.Transaction.Line, pos: Int)
    }
}