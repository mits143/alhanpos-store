package com.alhanpos.store.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemAddStockSearchBinding
import com.alhanpos.store.model.response.product.ProductListResponse.ProductListResponseItem


class AddStockTransferAdapter(
    var dataList: ArrayList<ProductListResponseItem>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<AddStockTransferAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAddStockSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddStockSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtName.text = "$name\n $subSku"
                quantity = if (quantity == 0) 1 else quantity
                binding.txtQty.setText(quantity.toString())
                binding.txtPrice.text = sellingPrice
                price = (quantity.toFloat() * sellingPrice!!.toFloat()).toString()
                binding.txtTotal.text = price
                binding.txtDelete.setOnClickListener {
                    buttonClick.onClick(dataList, position)
                    binding.txtQty.clearFocus()
                    removeItem(position)
                }

                binding.txtQty.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().isEmpty())
                            return
                        quantity = s.toString().toInt()
                        price = (quantity.toFloat() * sellingPrice.toFloat()).toString()
                        binding.txtTotal.text = price
//                        price =
//                            (quantity.toFloat() * product_variations[0].variations[0].sell_price_inc_tax.toFloat()).toString()

//                        notifyItemChanged(absoluteAdapterPosition)
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
                })
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
        fun onClick(data: ArrayList<ProductListResponseItem>, position: Int)
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }
}