package com.example.ecommerce.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ecommerce.data.local.Converters

@Entity(tableName = "orders")
@TypeConverters(Converters::class)
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val address: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
