package com.example.ecommerce.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerce.data.model.Order
import com.example.ecommerce.data.model.Product
import com.example.ecommerce.data.model.User

@Database(entities = [User::class, Product::class, Order::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_db"
                )
                .fallbackToDestructiveMigration() // Automatically handles schema changes by clearing the database
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
