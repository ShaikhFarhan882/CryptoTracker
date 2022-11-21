package com.example.cryptotracker.viewmodel


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.model.CryptoResponse
import com.example.cryptotracker.model.Data
import com.example.cryptotracker.repo.Repository
import com.example.cryptotracker.utils.GetContext
import com.example.cryptotracker.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class CryptoViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    val cryptoList : MutableLiveData<Resource<CryptoResponse>> = MutableLiveData()



    init {
        getData()
    }

    /*// Searching the cryptocurrency
      fun searchCrypto(query : String) : LiveData<List<Data>> {
          return Transformations.map(cryptoList) {
             it.data.filter {
                 it.name!!.contains(query)
             }
          }
      }*/


    //Api operation
    /*   fun getData() = viewModelScope.launch {
           val response = repository.getCryptoData()
           if(response.isSuccessful){
               response.body()?.let {
                   cryptoList.postValue(response.body())
               }
           }
       }*/


    fun getData() = viewModelScope.launch {
        safeCall()
    }

    //Handling the response
    private fun handleResponse(response: Response<CryptoResponse>) : Resource<CryptoResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    //Only call when internet is available
    private suspend fun safeCall() {
        cryptoList.postValue(Resource.Loading())
        try {
            if (checkForConnectivity()) {
                val response = repository.getCryptoData()
                cryptoList.postValue(handleResponse(response))
            } else {
                cryptoList.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (e: IOException) {
            when (e) {
                is IOException -> cryptoList.postValue(Resource.Error("Network Failure"))
                else -> cryptoList.postValue(Resource.Error("Data Conversion Error"))
            }

        }
    }


    //Room Database Operations
    fun upsert(data: Data) = viewModelScope.launch {
        repository.upsert(data)
    }

    fun delete(data: Data) = viewModelScope.launch {
        repository.delete(data)
    }

    fun getSavedCrypto() = repository.getSavedCrypto()


    //Internet Connectivity Conformation
    fun checkForConnectivity(): Boolean {
        var result = false
        val connectivityManager = getApplication<GetContext>().applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager?

        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    else -> false
                }
            }
        }
        return result
    }

}