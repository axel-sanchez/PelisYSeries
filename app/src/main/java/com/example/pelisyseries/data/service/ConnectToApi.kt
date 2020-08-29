package com.example.pelisyseries.data.service

import androidx.lifecycle.MutableLiveData
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.ui.interfaces.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "a0de5a9fe43359e41cb94081d6bafc05" //(AUTH V3)
/**
 * Esta clase es la encargada de conectarse a las api's
 * @author Axel Sanchez
 */
class ConnectToApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service: ApiService = retrofit.create(ApiService::class.java)

    /**
     * Esta función es la encargada de retornar las movies mas populares
     * @return devuelve un mutableLiveData que contiene un listado de movies populares
     * @sample getPopular("a0de5a9fe43359e41cb94081d6bafc05")
     */
    suspend fun getPopular(): MutableLiveData<List<Movie>> {
        var mutableLiveData = MutableLiveData<List<Movie>>()
        var response = service.getPopular(API_KEY)
        mutableLiveData.value = response.results
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar las movies mejor calificadas
     * @return devuelve un mutableLiveData que contiene un listado de movies mejor calificadas
     * @sample getTopRated("a0de5a9fe43359e41cb94081d6bafc05")
     */
    suspend fun getTopRated(): MutableLiveData<List<Movie>> {
        var mutableLiveData = MutableLiveData<List<Movie>>()
        var response = service.getTopRated(API_KEY)
        mutableLiveData.value = response.results
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar las movies próximas a estrenar
     * @return devuelve un mutableLiveData que contiene un listado de movies próximas a estrenar
     * @sample getUpcoming("a0de5a9fe43359e41cb94081d6bafc05")
     */
    suspend fun getUpcoming(): MutableLiveData<List<Movie>> {
        var mutableLiveData = MutableLiveData<List<Movie>>()
        var response = service.getUpcoming(API_KEY)
        mutableLiveData.value = response.results
        return mutableLiveData
    }

    companion object {
        /**
         * Utilizo Singleton para crear solo una instancia de esta clase
         */
        private var instance: ConnectToApi? = null

        fun getInstance(): ConnectToApi {
            if (instance == null) {
                instance = ConnectToApi()
            }
            return instance!!
        }
    }
}