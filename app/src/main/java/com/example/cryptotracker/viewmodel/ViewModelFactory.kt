package com.example.cryptotracker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.repo.Repository

class ViewModelFactory(private val repository: Repository,private val application : Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CryptoViewModel(repository,application) as T
    }
}