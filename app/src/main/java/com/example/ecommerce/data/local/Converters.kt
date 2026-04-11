package com.example.ecommerce.data.local

import androidx.room.TypeConverter
import com.example.ecommerce.data.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCartItemList(value: List<CartItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCartItemList(value: String): List<CartItem> {
        val listType = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
