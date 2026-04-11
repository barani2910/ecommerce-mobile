package com.example.ecommerce.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.data.model.Order
import com.example.ecommerce.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var orders: List<Order> = emptyList()

    fun submitList(newList: List<Order>) {
        orders = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.apply {
                tvOrderId.text = "Order #${order.id}"
                tvOrderTotal.text = "Total: $${String.format("%.2f", order.totalPrice)}"
                
                val date = Date(order.timestamp)
                val format = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                tvOrderDate.text = format.format(date)

                val itemsSummary = order.items.joinToString { it.product.name }
                tvOrderItems.text = itemsSummary
            }
        }
    }
}
