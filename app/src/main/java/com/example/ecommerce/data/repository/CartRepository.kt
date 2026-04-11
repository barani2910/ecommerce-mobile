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
        val index = currentItems.indexOfFirst { it.product.id == product.id }

        if (index != -1) {
            val item = currentItems[index]
            currentItems[index] = item.copy(quantity = item.quantity + 1)
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
        val index = currentItems.indexOfFirst { it.product.id == productId }
        
        if (index != -1) {
            if (quantity <= 0) {
                currentItems.removeAt(index)
            } else {
                currentItems[index] = currentItems[index].copy(quantity = quantity)
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
