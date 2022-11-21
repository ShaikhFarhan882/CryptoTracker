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

    companion object{
        @Volatile
        private var INSTANCE: CryptoDatabase? = null

        fun getDatabase(context: Context): CryptoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CryptoDatabase::class.java,
                        "crypto_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}