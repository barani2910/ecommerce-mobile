package com.example.ecommerce.data.repository

import android.content.Context
import com.example.ecommerce.data.local.AppDatabase
import com.example.ecommerce.data.local.PreferenceManager
import com.example.ecommerce.data.model.Order

class OrderRepository(context: Context) {
    private val orderDao = AppDatabase.getDatabase(context).orderDao()
    private val preferenceManager = PreferenceManager(context)

    suspend fun placeOrder(order: Order): Result<Unit> {
        return try {
            val email = preferenceManager.getUserEmail() ?: throw Exception("User not logged in")
            val orderWithUser = order.copy(userId = email)
            orderDao.insertOrder(orderWithUser)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrders(): Result<List<Order>> {
        return try {
            val email = preferenceManager.getUserEmail() ?: throw Exception("User not logged in")
            val orders = orderDao.getOrdersByUserId(email)
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
