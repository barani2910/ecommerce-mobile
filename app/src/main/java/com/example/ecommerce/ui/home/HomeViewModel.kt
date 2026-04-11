package com.example.ecommerce.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Product
import com.example.ecommerce.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository(application)

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProducts()
            if (result.isSuccess) {
                _products.value = result.getOrDefault(emptyList())
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProductsByCategory(category)
            if (result.isSuccess) {
                _products.value = result.getOrDefault(emptyList())
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
