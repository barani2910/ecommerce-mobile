package com.example.ecommerce.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce.data.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getOrdersByUserId(userId: String): List<Order>
}
