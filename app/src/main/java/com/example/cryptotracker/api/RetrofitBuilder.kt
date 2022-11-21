package com.example.cryptotracker.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitBuilder {

    companion object{
     private const val BASE_URL = "https://api.coincap.io/v2/"
        private val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        val API by lazy {
            retrofit.create(CryptoService::class.java)
        }
    }
}