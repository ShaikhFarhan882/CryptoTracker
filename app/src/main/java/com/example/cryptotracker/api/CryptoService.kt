package com.example.cryptotracker.api

import com.example.cryptotracker.model.CryptoResponse

import retrofit2.Response
import retrofit2.http.GET

interface CryptoService {

    //https://api.coincap.io/v2/assets

    @GET("assets")
    suspend fun getCryptoData() : Response<CryptoResponse>

}