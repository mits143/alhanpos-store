package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.R
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
                if (absoluteAdapterPosition == 0) {
                    binding.imgProduct.setImageResource(R.drawable.whir)
                    val layoutParams = LinearLayout.LayoutParams(150, 150)
                    binding.imgProduct.layoutParams = layoutParams
                    binding.txtProduct.text = "Add"
                    binding.txtPrice.visibility = View.GONE
                    binding.txtProduct.setTextColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.white
                        )
                    )
                    binding.cvProduct.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.btnColor
                        )
                    )

                    binding.cvProduct.setOnClickListener {
                        buttonClick.onClick()
                    }
                } else {
                    binding.txtProduct.text = this.name
    //                    binding.txtPrice.text =
    //                        this.product_variations[0].variations[0].default_sell_price
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