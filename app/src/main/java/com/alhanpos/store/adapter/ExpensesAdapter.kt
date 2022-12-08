package com.alhanpos.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhanpos.store.databinding.ItemExpenseBinding
import com.alhanpos.store.model.response.expenses.Data

class ExpensesAdapter(
    var dataList: ArrayList<Data>,
    var buttonClick: ButtonClick
) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                binding.txtDate.text = this.transactionDate
                binding.txtRefNo.text = this.refNo
                binding.txtRecurring.text = this.recurInterval
                binding.txtExpCat.text = this.category
                binding.txtNote.text = this.additionalNotes
                binding.txtPaymentStatus.text = this.paymentStatus
                binding.txtTax.text = this.tax
                binding.txtTotalAmt.text = this.finalTotal
                binding.txtPaymentDue.text = "0"
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(list: ArrayList<Data>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: ArrayList<Data>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    interface ButtonClick {
        fun onEditClick(data: Data)
        fun onDeleteClick(data: Data, pos: Int)
    }
}