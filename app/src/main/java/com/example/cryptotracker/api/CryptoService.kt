package com.example.cryptotracker.api

import com.example.cryptotracker.model.CryptoResponse
import com.example.cryptotracker.model.search.SearchResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {

    //https://api.coincap.io/v2/assets

    // https://api.coincap.io/v2/assets/dogecoin //Path

    @GET("assets")
    suspend fun getCryptoData() : Response<CryptoResponse>

    @GET("assets/{id}")
    suspend fun searchCrypto(@Path("id") id : String) : Response<SearchResponse>

}