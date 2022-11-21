package com.example.cryptotracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cryptotracker.model.Data


@Dao
interface CryptoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data : Data) : Long

    @Delete
    suspend fun deleteCrypto(data: Data)

    @Query("SELECT * FROM crypto")
    fun getSavedCrypto() : LiveData<List<Data>>
}