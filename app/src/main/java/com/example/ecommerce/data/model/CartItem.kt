package com.example.ecommerce.data.model

data class CartItem(
    val id: String = "",
    val product: Product = Product(),
    var quantity: Int = 1
)
