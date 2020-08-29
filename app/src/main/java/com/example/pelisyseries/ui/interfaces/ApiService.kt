package com.example.pelisyseries.ui.interfaces

import com.example.pelisyseries.data.models.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface para generar las peticiones a la api
 * @author Axel Sanchez
 */
interface ApiService {
    /**
     * Api que devuelve las peliculas más populares
     * Solo es necesario pasar el appi_key
     * @param [apiKey] código que nos da la página de la api
     */
    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String): Result

    /**
     * Api que devuelve las peliculas mejor calificadas
     * Solo es necesario pasar el appi_key
     * @param [apiKey] código que nos da la página de la api
     */
    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") apiKey: String): Result

    /**
     * Api que devuelve las peliculas próximas a estrenar
     * Solo es necesario pasar el appi_key
     * @param [apiKey] código que nos da la página de la api
     */
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): Result

    /**
     * Example = https://api.themoviedb.org/3/movie/550?api_key=a0de5a9fe43359e41cb94081d6bafc05
     * Token = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMGRlNWE5ZmU0MzM1OWU0MWNiOTQwODFkNmJhZmMwNSIsInN1YiI6IjVmNDk4MjNlMzliNmMzMDAzNjk2ODA0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hsvg8iJYNb_926YkxtF59ogCUNxXvA8NDAZObZK00ok
     */
}