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
     * Esta función es la encargada de retornar el clima de hoy y los 5 días siguientes
     * @param [lat] recibe la latitud de la ubicación
     * @param [lon] recibe la longitud de la ubicación
     * @return devuelve un mutableLiveData de la Base que es el objeto que mapea la respuesta
     * @sample getPopular("a0de5a9fe43359e41cb94081d6bafc05")
     */
    suspend fun getPopular(): MutableLiveData<List<Movie>> {
        var mutableLiveData = MutableLiveData<List<Movie>>()
        var response = service.getPopular(API_KEY)
        try {
            mutableLiveData.value = response.results
        } catch (e: Exception){
            e.printStackTrace()
        }
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