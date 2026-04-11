package com.example.ecommerce.data.model

data class CartItem(
    val id: String = "",
    val product: Product = Product(),
    val quantity: Int = 1
)
