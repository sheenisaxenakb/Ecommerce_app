package com.ecommerceapp.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerceapp.network.ProductRepository
import com.ecommerceapp.ui.model.CartItem
import com.ecommerceapp.ui.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ECommerceViewModel(private val repository: ProductRepository) : ViewModel() {
    val cart = mutableStateListOf<CartItem>()
    val discountCode = mutableStateOf("")
    val errorMessage = mutableStateOf("")

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    /* fun loadProducts() {
         // Mocked product list; Replace with actual API call

         products.addAll(
             listOf(
                 Product(1, "Quinoa Fruit Salad", 20000.0, "https://via.placeholder.com/150"),
                 Product(2, "Melon Fruit Salad", 20000.0, "https://via.placeholder.com/150"),
                 Product(3, "Tropical Fruit Salad", 20000.0, "https://via.placeholder.com/150")
             )
         )
     }*/

    fun addToCart(product: Product) {
        val cartItem = cart.find { it.product.id == product.id }
        if (cartItem != null) {
            cartItem.quantity++
        } else {
            cart.add(CartItem(product, 1))
        }
    }

    fun removeFromCart(product: Product) {
        val cartItem = cart.find { it.product.id == product.id }
        if (cartItem != null) {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
            } else {
                cart.remove(cartItem)
            }
        }
    }

    fun applyDiscount(): Double {
        val total = cart.sumOf { it.product.price * it.quantity }
        return when (discountCode.value) {
            "SAVE20" -> total * 0.8
            "FLAT100" -> if (total > 500) total - 100 else total
            "INVALID" -> {
                errorMessage.value = "Invalid discount code"
                total
            }

            else -> 0.0
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            repository.getProductsFlow()
                .catch { e ->
                    // Handle errors here if needed
                    _products.value = emptyList()
                }
                .collect { productList ->
                    _products.value = productList
                }
        }
    }
}
