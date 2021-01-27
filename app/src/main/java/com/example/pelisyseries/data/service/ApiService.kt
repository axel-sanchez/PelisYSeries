package com.example.pelisyseries.data.service

import com.example.pelisyseries.data.models.Result
import com.example.pelisyseries.data.models.VideoResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface para generar las peticiones a la api
 * @author Axel Sanchez
 */
interface ApiService {
    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String): Response<Result>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") apiKey: String): Response<Result>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): Response<Result>

    @GET("search/movie")
    suspend fun search(@Query("api_key") apiKey: String, @Query("query") query: String): Response<Result>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<VideoResult>


    //TODO: FUNCIONES CREADAS PARA HACER TEST


    @GET("movie/popular")
    fun getPopularTest(@Query("api_key") apiKey: String): Call<Result>

    @GET("movie/top_rated")
    fun getTopRatedTest(@Query("api_key") apiKey: String): Call<Result>

    @GET("movie/upcoming")
    fun getUpcomingTest(@Query("api_key") apiKey: String): Call<Result>

    @GET("search/movie")
    fun searchTest(@Query("api_key") apiKey: String, @Query("query") query: String): Call<Result>

    @GET("movie/{movie_id}/videos")
    fun getVideoTest(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Call<VideoResult>

    /*
     * Example = https://api.themoviedb.org/3/movie/550?api_key=a0de5a9fe43359e41cb94081d6bafc05
     * Token = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMGRlNWE5ZmU0MzM1OWU0MWNiOTQwODFkNmJhZmMwNSIsInN1YiI6IjVmNDk4MjNlMzliNmMzMDAzNjk2ODA0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hsvg8iJYNb_926YkxtF59ogCUNxXvA8NDAZObZK00ok
     */
}