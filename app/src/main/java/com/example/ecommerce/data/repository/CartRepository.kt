package com.example.ecommerce.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.data.model.CartItem
import com.example.ecommerce.data.model.Product

object CartRepository {
    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentItems.add(CartItem(id = product.id, product = product, quantity = 1))
        }
        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        currentItems.removeAll { it.product.id == productId }
        _cartItems.value = currentItems
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val item = currentItems.find { it.product.id == productId }
        if (item != null) {
            if (quantity <= 0) {
                currentItems.remove(item)
            } else {
                item.quantity = quantity
            }
        }
        _cartItems.value = currentItems
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotalPrice(): Double {
        return _cartItems.value?.sumOf { it.product.price * it.quantity } ?: 0.0
    }
}
