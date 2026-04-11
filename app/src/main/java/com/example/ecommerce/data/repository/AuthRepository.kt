package com.example.ecommerce.data.repository

import android.content.Context
import com.example.ecommerce.data.local.AppDatabase
import com.example.ecommerce.data.local.PreferenceManager
import com.example.ecommerce.data.model.User

class AuthRepository(context: Context) {
    private val userDao = AppDatabase.getDatabase(context).userDao()
    private val preferenceManager = PreferenceManager(context)

    suspend fun login(email: String, pass: String): Result<User?> {
        val user = userDao.getUserByEmail(email)
        return if (user != null && user.password == pass) {
            preferenceManager.saveUserEmail(email)
            Result.success(user)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }

    suspend fun register(email: String, pass: String): Result<User?> {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            return Result.failure(Exception("User already exists"))
        }
        val newUser = User(email, pass)
        userDao.registerUser(newUser)
        preferenceManager.saveUserEmail(email)
        return Result.success(newUser)
    }

    fun logout() {
        preferenceManager.logout()
    }

    suspend fun getCurrentUser(): User? {
        val email = preferenceManager.getUserEmail() ?: return null
        return userDao.getUserByEmail(email)
    }

    fun isLoggedIn(): Boolean = preferenceManager.isLoggedIn()
}
