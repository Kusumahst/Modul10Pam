package com.practicum.apimahasiswa.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    @JvmStatic
    val apiService: ApiService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.20:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
}