package com.example.cryptotracker.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptotracker.database.CryptoDAO
import com.example.cryptotracker.database.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDao(providesDatabase: CryptoDatabase) : CryptoDAO {
     return providesDatabase.cryptoDAO()
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext appContext : Context) : CryptoDatabase {
       return Room.databaseBuilder(
           appContext,
           CryptoDatabase::class.java,
           "crypto_db"
       ).fallbackToDestructiveMigration().build()
    }
}