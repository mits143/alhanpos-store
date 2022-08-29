package com.alhanpos.store.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.R
import com.google.android.material.card.MaterialCardView


class MainAdapter(
    private val users: ArrayList<String>,
    private var type: String
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: String, type: String) {
            if (TextUtils.equals(type, "TYPE_ALL")) {
                val imageView = itemView.findViewById<ImageView>(R.id.imgProduct)
                val textView = itemView.findViewById<TextView>(R.id.txtProduct)
                val textView1 = itemView.findViewById<TextView>(R.id.txtPrice)
                val cvProduct = itemView.findViewById<MaterialCardView>(R.id.cvProduct)

                if (TextUtils.equals(user, "Add")) {
                    imageView.setImageResource(R.drawable.whir)
                    val layoutParams = LinearLayout.LayoutParams(150, 150)
                    imageView.layoutParams = layoutParams
                    textView.text = user
                    textView1.visibility = View.GONE
                    textView.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                    cvProduct.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.btnColor
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            if (TextUtils.equals(type, "TYPE_POS")) {
                LayoutInflater.from(parent.context).inflate(R.layout.item_pos, parent, false)
            } else if (TextUtils.equals(type, "TYPE_ALL")) {
                LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            } else if (TextUtils.equals(type, "TYPE_SUBSCRIPTION")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subscription, parent, false)
            } else if (TextUtils.equals(type, "TYPE_STOCK_TRANSFER")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_stock_transfer, parent, false)
            } else if (TextUtils.equals(type, "TYPE_CATEGORY") || TextUtils.equals(
                    type,
                    "TYPE_BRAND"
                )
            ) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category_brand, parent, false)
            } else if (TextUtils.equals(type, "TYPE_PURCHASE_ORDER")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_order, parent, false)
            } else if (TextUtils.equals(type, "TYPE_ALL_SALE")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sale, parent, false)
            } else if (TextUtils.equals(type, "TYPE_MANAGE_USERS")) {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_manage_users, parent, false)
            } else {
                throw IllegalArgumentException("Invalid type")
            }
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position], type)

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: List<String>) {
        users.clear()
        users.add(0, "Add")
        users.addAll(list)
        notifyDataSetChanged()
    }
}