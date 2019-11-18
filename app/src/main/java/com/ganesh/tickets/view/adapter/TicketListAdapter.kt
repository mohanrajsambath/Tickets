package com.ganesh.tickets.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.tickets.R
import com.ganesh.tickets.model.TicketModel
import kotlinx.android.synthetic.main.ticket_adapter.view.*
import javax.inject.Inject

class TicketListAdapter
    @Inject constructor(): RecyclerView.Adapter<TicketListAdapter.UserHolder>(),
    BindableAdapter<TicketModel> {

    private lateinit var itemSelectedCallback: OnTicketItemSelected
    private var ticketItems = emptyList<TicketModel>()

    override fun setData(items: List<TicketModel>) {
        ticketItems = items
        notifyDataSetChanged()
    }

    fun setCallBack(callback: OnTicketItemSelected) {
        this.itemSelectedCallback = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserHolder(inflater.inflate(R.layout.ticket_adapter, parent, false))
    }

    override fun getItemCount() = ticketItems.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(ticketItems[position], itemSelectedCallback)
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var callback: OnTicketItemSelected

        @SuppressLint("SetTextI18n")
        fun bind(model: TicketModel, callback: OnTicketItemSelected) {
            itemView.name.text = "Customer Name: ${model.customer_name}"
            itemView.desc.text = "Order ID: ${model.order_id}"
            itemView.tag = model
            this.callback = callback
            itemView.setOnClickListener(this::onSelected)
        }

        private fun onSelected(view: View) {
            val selectedItem = view.tag as TicketModel
            callback.onItemSelected(selectedItem)
        }

    }


}

interface OnTicketItemSelected {
    fun onItemSelected(selectedItem: TicketModel)
}