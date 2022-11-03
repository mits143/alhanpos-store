package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemProductBinding
import com.alhanpos.store.model.response.product.ProductData

class ProductAdapter(
    var dataList: ArrayList<ProductData>, var buttonClick: ButtonClick
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtProduct.text = this.name.toString()
                binding.txtSKU.text = this.sku.toString()
                binding.txtQuantity.text = this.alert_quantity.toString()
                if (this.brand != null) {
                    binding.txtBrand.text = this.brand.name
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

    fun addData(list: List<ProductData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface ButtonClick {
        fun onClick()
    }
}