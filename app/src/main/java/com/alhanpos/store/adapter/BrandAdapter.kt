package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemCategoryBrandBinding
import com.alhanpos.store.model.response.brand.BrandResponseItem

class BrandAdapter(
    var dataList: ArrayList<BrandResponseItem>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBrandBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtCategory.text = this.name
//                binding.txtCode.text = this.shortCode
                binding.txtCode.visibility = View.GONE
                binding.txtDesc.text = this.description

                binding.txtEdit.setOnClickListener {
                    buttonClick.onEditClick(this)
                }

                binding.txtDelete.setOnClickListener {
                    buttonClick.onDeleteClick(this, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<BrandResponseItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<BrandResponseItem>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    interface ButtonClick {
        fun onEditClick(data: BrandResponseItem)
        fun onDeleteClick(data: BrandResponseItem, pos: Int)
    }
}