package com.example.cryptotracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptotracker.model.Data

@Database(
    entities = [Data::class],
    version = 3,
    exportSchema = false
)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDAO() : CryptoDAO

}