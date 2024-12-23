package com.ecommerceapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://119edbb1-1037-4fa8-bd73-f661b0654ba6.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: BaseService = retrofit.create(BaseService::class.java)
}
