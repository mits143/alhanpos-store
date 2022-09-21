package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemContactBinding
import com.alhanpos.store.model.response.contact.ContactData

class ContactAdapter(
    var dataList: ArrayList<ContactData>,
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtID.text = this.id.toString()
                binding.txtBusiness.text = this.supplier_business_name ?: "-"
                binding.txtName.text = this.name ?: "-"
                binding.txtEmail.text = this.email ?: "-"
                binding.txtTax.text = this.tax_number ?: "-"
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: List<ContactData>) {
        dataList.clear()
        dataList.addAll(list)
//        notifyItemInserted(list.size - itemCount - 1);
        notifyDataSetChanged()
    }
}