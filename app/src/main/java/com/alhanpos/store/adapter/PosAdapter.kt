package com.alhanpos.store.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemPosBinding
import com.alhanpos.store.model.response.product.ProductData

class PosAdapter(
    var dataList: ArrayList<ProductData>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<PosAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtName.text = "$name $sku"
                quantity = if (quantity == 0) 1 else quantity
                binding.txtQty.setText(quantity.toString())
                price =
                    (quantity.toFloat() * product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()
                binding.txtPrice.text = price

//                binding.txtQty.addTextChangedListener(object : TextWatcher {
//                    override fun afterTextChanged(s: Editable?) {
//                        if (s.toString().isEmpty())
//                            return
//                        quantity = s.toString().toInt()
////                        price =
////                            (quantity.toFloat() * product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()
//
//                        notifyItemChanged(absoluteAdapterPosition)
//                    }
//
//                    override fun beforeTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        count: Int,
//                        after: Int
//                    ) {
//                    }
//
//                    override fun onTextChanged(
//                        s: CharSequence?,
//                        start: Int,
//                        before: Int,
//                        count: Int
//                    ) {
//                    }
//                })
                binding.imgDecrease.setOnClickListener {
                    if (quantity == 1)
                        return@setOnClickListener
                    quantity = (quantity.minus(1))
                    notifyItemChanged(absoluteAdapterPosition)
                    buttonClick.onClick(this@with)
                }
                binding.imgIncrease.setOnClickListener {
                    quantity = (quantity.plus(1))
                    notifyItemChanged(absoluteAdapterPosition)
                    buttonClick.onClick(this@with)
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface ButtonClick {
        fun onClick(data: ProductData)
    }
}