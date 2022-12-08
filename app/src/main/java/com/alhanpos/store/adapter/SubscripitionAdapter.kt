package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemSubscriptionBinding
import com.alhanpos.store.model.response.subscription.Data

class SubscripitionAdapter(
    var dataList: ArrayList<Data>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<SubscripitionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSubscriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtBrand.text = this.packageName
                binding.txtSubNo.text = this.businessId
                binding.txtCustName.text = this.createdBy
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