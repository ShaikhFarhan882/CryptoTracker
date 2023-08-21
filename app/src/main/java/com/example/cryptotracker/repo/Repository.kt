package com.example.cryptotracker.repo

import androidx.lifecycle.LiveData
import com.example.cryptotracker.api.CryptoService
import com.example.cryptotracker.database.CryptoDatabase
import com.example.cryptotracker.model.Data
import javax.inject.Inject

class Repository @Inject constructor(val cryptoService: CryptoService, private val cryptoDatabase: CryptoDatabase) {

    suspend fun getCryptoData() = cryptoService.getCryptoData()

    suspend fun searchCrypto(id : String) = cryptoService.searchCrypto(id)


    suspend fun upsert(data: Data) = cryptoDatabase.cryptoDAO().upsert(data)

    fun getSavedCrypto() : LiveData<List<Data>> = cryptoDatabase.cryptoDAO().getSavedCrypto()

    suspend fun delete(data: Data) = cryptoDatabase.cryptoDAO().deleteCrypto(data)

    suspend fun deleteAll() = cryptoDatabase.cryptoDAO().deleteAllCurrency()


}