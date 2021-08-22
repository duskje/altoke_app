package com.altoke.altoke

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CustomerOrdersAdapter(private var orders: List<Order>) : RecyclerView.Adapter<CustomerOrdersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView as CustomerOrderElementView
        val setOrder = {order: Order -> view.setOrder(order)}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order: Order = orders.get(position)

        holder.setOrder(order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CustomerOrderElementView(parent.context))
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun update(newList: List<Order>){
        val callback = CustomerOrdersDiffUtilCallback(orders, newList)
        val diff = DiffUtil.calculateDiff(callback)
        orders = newList

        diff.dispatchUpdatesTo(this)
    }
}