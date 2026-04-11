package com.example.ecommerce.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString("user_email", email).apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("user_email", null)
    }

    fun logout() {
        sharedPreferences.edit().remove("user_email").apply()
    }

    fun isLoggedIn(): Boolean {
        return getUserEmail() != null
    }
}
