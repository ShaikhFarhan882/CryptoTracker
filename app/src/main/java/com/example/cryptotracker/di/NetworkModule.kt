package com.example.cryptotracker.di

import com.example.cryptotracker.api.CryptoService
import com.example.cryptotracker.utils.Constants.BASE_URL_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(providesRetrofit: Retrofit) : CryptoService{
        return providesRetrofit.create(CryptoService::class.java)
    }


}