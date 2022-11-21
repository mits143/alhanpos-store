package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemProductBinding
import com.alhanpos.store.model.response.product.ProductListResponseItem

class ProductAdapter(
    var dataList: ArrayList<ProductListResponseItem>, var buttonClick: ButtonClick
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtProduct.text = this.name
                binding.txtSKU.text = this.subSku
                binding.txtQuantity.text = this.qtyAvailable?:""
//                if (this.brand != null) {
//                    binding.txtBrand.text = this.brand.name
//                }
                binding.imgEdit.setOnClickListener {
                    buttonClick.onClick(this)
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

    fun addData(list: List<ProductListResponseItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    interface ButtonClick {
        fun onClick(data: ProductListResponseItem)
    }
}