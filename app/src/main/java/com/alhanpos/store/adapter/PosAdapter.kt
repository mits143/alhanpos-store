package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemPosNewBinding
import com.alhanpos.store.model.response.product.ProductListResponseItem

class PosAdapter(
    var dataList: ArrayList<ProductListResponseItem>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<PosAdapter.ViewHolder>() {
    var x = 0f

    inner class ViewHolder(val binding: ItemPosNewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPosNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtProduct.text = this.name
                binding.txtSKU.text = this.sellingPrice

                if (isAdded) {
                    binding.view.visibility = View.VISIBLE
                    binding.txtQty.visibility = View.VISIBLE
                } else {
                    binding.view.visibility = View.GONE
                    binding.txtQty.visibility = View.GONE
                }
                itemView.setOnClickListener {
                    buttonClick.onClick(this, position)
                }

                /*binding.txtQty.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().isEmpty())
                            return
                        quantity = s.toString().toInt()
//                        price =
//                            (quantity.toFloat() * product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()

                        notifyItemChanged(absoluteAdapterPosition)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })*/

//                binding.imgDecrease.setOnClickListener {
//                    if (quantity == 1)
//                        return@setOnClickListener
//                    quantity = (quantity.minus(1))
//                    notifyItemChanged(absoluteAdapterPosition)
//                }
//                binding.imgIncrease.setOnClickListener {
//                    quantity = (quantity.plus(1))
//                    notifyItemChanged(absoluteAdapterPosition)
//                }
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
        fun onClick(data: ProductListResponseItem, position: Int)
    }
}