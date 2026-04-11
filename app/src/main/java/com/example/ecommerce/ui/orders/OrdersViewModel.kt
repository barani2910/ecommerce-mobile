package com.example.ecommerce.ui.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Order
import com.example.ecommerce.data.repository.OrderRepository
import kotlinx.coroutines.launch

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = OrderRepository(application)

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getOrders()
            if (result.isSuccess) {
                _orders.value = result.getOrDefault(emptyList())
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
