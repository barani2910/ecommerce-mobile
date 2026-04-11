package com.example.ecommerce.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.User
import com.example.ecommerce.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application)

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        viewModelScope.launch {
            _user.value = repository.getCurrentUser()
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.login(email, pass)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun register(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.register(email, pass)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        repository.logout()
        _user.value = null
    }

    fun clearError() {
        _error.value = null
    }
}
