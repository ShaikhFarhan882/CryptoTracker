package com.example.cryptotracker.model

data class CryptoResponse(
    val `data`: MutableList<Data>,
    val timestamp: Long
)