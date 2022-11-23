package com.example.cryptotracker.repo

import androidx.lifecycle.LiveData
import com.example.cryptotracker.api.RetrofitBuilder
import com.example.cryptotracker.database.CryptoDatabase
import com.example.cryptotracker.model.Data

class Repository(private val cryptoDatabase: CryptoDatabase) {

    suspend fun getCryptoData() = RetrofitBuilder.API.getCryptoData()

    suspend fun upsert(data: Data) = cryptoDatabase.cryptoDAO().upsert(data)

    fun getSavedCrypto() : LiveData<List<Data>> = cryptoDatabase.cryptoDAO().getSavedCrypto()

    suspend fun delete(data: Data) = cryptoDatabase.cryptoDAO().deleteCrypto(data)

    suspend fun deleteAll() = cryptoDatabase.cryptoDAO().deleteAllCurrency()


}