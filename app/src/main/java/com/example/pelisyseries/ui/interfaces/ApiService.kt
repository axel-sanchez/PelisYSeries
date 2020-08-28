package com.example.pelisyseries.ui.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface para generar las peticiones a la api
 * @author Axel Sanchez
 */
interface ApiService {
    @GET
    fun getMovies()
    //fun getWeatherTest(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Call<Base>
}