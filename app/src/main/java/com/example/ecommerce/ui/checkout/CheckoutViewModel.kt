package com.example.ecommerce.ui.checkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.Order
import com.example.ecommerce.data.repository.CartRepository
import com.example.ecommerce.data.repository.OrderRepository
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = OrderRepository(application)

    private val _orderPlaced = MutableLiveData<Boolean>()
    val orderPlaced: LiveData<Boolean> = _orderPlaced

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun placeOrder() {
        val cartItems = CartRepository.cartItems.value ?: emptyList()
        if (cartItems.isEmpty()) return

        val order = Order(
            items = cartItems,
            totalPrice = CartRepository.getTotalPrice()
        )

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.placeOrder(order)
            if (result.isSuccess) {
                CartRepository.clearCart()
                _orderPlaced.value = true
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
