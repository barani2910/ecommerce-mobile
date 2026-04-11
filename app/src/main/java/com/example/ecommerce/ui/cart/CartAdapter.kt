package com.example.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.data.model.CartItem
import com.example.ecommerce.databinding.ItemCartBinding

class CartAdapter(
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items: List<CartItem> = emptyList()

    fun submitList(newList: List<CartItem>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.apply {
                tvProductName.text = cartItem.product.name
                tvProductPrice.text = "$${cartItem.product.price}"
                tvQuantity.text = cartItem.quantity.toString()
                
                Glide.with(ivProduct.context)
                    .load(cartItem.product.imageUrl)
                    .into(ivProduct)

                btnIncrease.setOnClickListener { onIncrease(cartItem) }
                btnDecrease.setOnClickListener { onDecrease(cartItem) }
                btnRemove.setOnClickListener { onRemove(cartItem) }
            }
        }
    }
}
