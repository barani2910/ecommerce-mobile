package com.example.ecommerce.data.repository

import android.content.Context
import com.example.ecommerce.data.local.AppDatabase
import com.example.ecommerce.data.model.Product

class ProductRepository(context: Context) {
    private val productDao = AppDatabase.getDatabase(context).productDao()

    suspend fun getProducts(): Result<List<Product>> {
        return try {
            var products = productDao.getAllProducts()
            if (products.isEmpty()) {
                // Pre-populate with dummy data if database is empty
                val dummyProducts = listOf(
                    Product("1", "Wireless Headphones", "Noise-canceling bluetooth headphones.", 199.99, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e", "Electronics"),
                    Product("2", "Cotton T-Shirt", "100% organic cotton, breathable.", 25.0, "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab", "Clothing"),
                    Product("3", "Smart Watch", "Track your fitness and notifications.", 149.0, "https://images.unsplash.com/photo-1523275335684-37898b6baf30", "Electronics"),
                    Product("4", "Running Shoes", "Comfortable and durable for long runs.", 85.0, "https://images.unsplash.com/photo-1542291026-7eec264c27ff", "Clothing")
                )
                productDao.insertProducts(dummyProducts)
                products = dummyProducts
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        return try {
            val products = productDao.getProductsByCategory(category)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
