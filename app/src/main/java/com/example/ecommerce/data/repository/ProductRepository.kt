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
                    Product("4", "Running Shoes", "Comfortable and durable for long runs.", 85.0, "https://images.unsplash.com/photo-1542291026-7eec264c27ff", "Clothing"),
                    Product("5", "Gaming Laptop", "High-performance laptop for gaming and work.", 1299.99, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8", "Electronics"),
                    Product("6", "Smartphone Pro", "Latest smartphone with amazing camera.", 999.0, "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9", "Electronics"),
                    Product("7", "Bluetooth Speaker", "Portable speaker with deep bass.", 49.99, "https://images.unsplash.com/photo-1608353522256-ac0e6c7c5ad2", "Electronics"),
                    Product("8", "Denim Jacket", "Classic blue denim jacket for all seasons.", 75.0, "https://images.unsplash.com/photo-1523206489230-c012c64b2b48", "Clothing"),
                    Product("9", "Leather Backpack", "Premium leather backpack for daily use.", 120.0, "https://images.unsplash.com/photo-1548036328-c9fa89d128fa", "Clothing"),
                    Product("10", "Mechanical Keyboard", "RGB mechanical keyboard for gaming.", 89.0, "https://images.unsplash.com/photo-1511467687858-23d96c32e4ae", "Electronics"),
                    Product("11", "Gaming Mouse", "Precision gaming mouse with side buttons.", 55.0, "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf", "Electronics"),
                    Product("12", "Yoga Mat", "Non-slip yoga mat for fitness.", 29.99, "https://images.unsplash.com/photo-1592432678016-e910b452f9a2", "Clothing"),
                    Product("13", "Stainless Water Bottle", "Insulated water bottle to keep drinks cold.", 19.99, "https://images.unsplash.com/photo-1602143399827-70590bbef94a", "Clothing"),
                    Product("14", "Winter Hoodie", "Warm and cozy fleece hoodie.", 45.0, "https://images.unsplash.com/photo-1556821840-3a63f95609a7", "Clothing"),
                    Product("15", "Tablet Air", "Lightweight tablet for entertainment.", 499.0, "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0", "Electronics"),
                    Product("16", "Power Bank", "Fast-charging portable power bank.", 39.99, "https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5", "Electronics"),
                    Product("17", "Summer Sunglasses", "UV protected stylish sunglasses.", 15.0, "https://images.unsplash.com/photo-1511499767390-a73359580ca4", "Clothing"),
                    Product("18", "Designer Sneakers", "Lightweight designer sneakers.", 110.0, "https://images.unsplash.com/photo-1549298916-b41d501d3772", "Clothing")
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
