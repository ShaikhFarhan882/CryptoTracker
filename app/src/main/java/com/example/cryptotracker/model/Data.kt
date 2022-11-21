package com.example.cryptotracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "crypto")
data class Data(
    val changePercent24Hr: String?,
    val explorer: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val marketCapUsd: String?,
    val maxSupply: String?,
    val name: String?,
    val priceUsd: String?,
    val rank: String?,
    val supply: String?,
    val symbol: String?,
    val volumeUsd24Hr: String?,
    val vwap24Hr: String?,

)