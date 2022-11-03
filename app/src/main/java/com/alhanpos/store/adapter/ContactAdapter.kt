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
//                binding.imgEdit.text = this.id.toString()
//                binding.imgCustomer.text = this.supplier_business_name ?: "-"
                binding.txtContactID.text = this.contact_id
                binding.txtPhoneNumber.text = this.mobile
                binding.txtEmail.text = this.email
                binding.txtAdvanceBalance.text = this.balance
                binding.txtTotalDue.text = this.total_due
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