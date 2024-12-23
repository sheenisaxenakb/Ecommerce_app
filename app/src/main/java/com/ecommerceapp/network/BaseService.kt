package com.ecommerceapp.network

import com.ecommerceapp.ui.model.Product
import retrofit2.Response

import retrofit2.http.GET

interface BaseService {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(
    ): Response<List<Product>>

    companion object {
        const val GET_PRODUCTS = "products"
    }
}