package com.ecommerceapp.network

import com.ecommerceapp.ui.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductRepository(private val api: BaseService) {

    fun getProductsFlow(): Flow<List<Product>> = flow {
        // Start loading data
        emit(emptyList()) // You can emit an empty list or a loading state
        try {
            val response = api.getProducts()
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            } else {
                throw Exception("API Error: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Network Error: ${e.message}")
        }
    }.catch { e ->
        // Emit or log errors here
        throw e
    }
}