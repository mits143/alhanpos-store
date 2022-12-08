package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemCategoryBrandBinding
import com.alhanpos.store.model.response.category.CategoryResponseItem

class CategoryAdapter(
    var dataList: ArrayList<CategoryResponseItem>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

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
                binding.txtCode.visibility = View.VISIBLE
                binding.txtCode.text = this.shortCode
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

    fun addData(list: ArrayList<CategoryResponseItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<CategoryResponseItem>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    interface ButtonClick {
        fun onEditClick(data: CategoryResponseItem)
        fun onDeleteClick(data: CategoryResponseItem, pos: Int)
    }
}