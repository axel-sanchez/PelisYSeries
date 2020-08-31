package com.example.pelisyseries.data.service

import com.example.pelisyseries.data.models.Result
import com.example.pelisyseries.data.models.VideoResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface para generar las peticiones a la api
 * @author Axel Sanchez
 */
interface ApiService {
    /**
     * Api que devuelve las peliculas más populares
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Result]
     */
    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String): Result

    /**
     * Api que devuelve las peliculas mejor calificadas
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Result]
     */
    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") apiKey: String): Result

    /**
     * Api que devuelve las peliculas próximas a estrenar
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Result]
     */
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): Result

    /**
     * Api que devuelve las peliculas que coinciden con la búsqueda
     * @param [apiKey] código que nos da la página de la api
     * @param [query] es el nombre de la pelicula
     * @return devuelve un [Result]
     */
    @GET("search/movie")
    suspend fun search(@Query("api_key") apiKey: String, @Query("query") query: String): Result

    /**
     * Api que devuelve el video de una pelicula
     * @param [apiKey] código que nos da la página de la api
     * @param [id] es el id de la pelicula
     * @return devuelve un [VideoResult]
     */
    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(@Path("movie_id") id: String, @Query("api_key") apiKey: String): VideoResult


    //TODO: FUNCIONES CREADAS PARA HACER TEST


    /**
     * Api de test que devuelve las peliculas más populares
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Call] de [Result]
     */
    @GET("movie/popular")
    fun getPopularTest(@Query("api_key") apiKey: String): Call<Result>

    /**
     * Api de  test que devuelve las peliculas mejor calificadas
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Call] de [Result]
     */
    @GET("movie/top_rated")
    fun getTopRatedTest(@Query("api_key") apiKey: String): Call<Result>

    /**
     * Api de test que devuelve las peliculas próximas a estrenar
     * @param [apiKey] código que nos da la página de la api
     * @return devuelve un [Call] de [Result]
     */
    @GET("movie/upcoming")
    fun getUpcomingTest(@Query("api_key") apiKey: String): Call<Result>

    /**
     * Api de test que devuelve las peliculas que coinciden con la búsqueda
     * @param [apiKey] código que nos da la página de la api
     * @param [query] es el nombre de la pelicula
     * @return devuelve un [Call] de [Result]
     */
    @GET("search/movie")
    fun searchTest(@Query("api_key") apiKey: String, @Query("query") query: String): Call<Result>

    /**
     * Api de test que devuelve el video de una pelicula
     * @param [apiKey] código que nos da la página de la api
     * @param [id] es el id de la pelicula
     * @return devuelve un [Call] de [VideoResult]
     */
    @GET("movie/{movie_id}/videos")
    fun getVideoTest(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Call<VideoResult>

    /*
     * Example = https://api.themoviedb.org/3/movie/550?api_key=a0de5a9fe43359e41cb94081d6bafc05
     * Token = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMGRlNWE5ZmU0MzM1OWU0MWNiOTQwODFkNmJhZmMwNSIsInN1YiI6IjVmNDk4MjNlMzliNmMzMDAzNjk2ODA0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hsvg8iJYNb_926YkxtF59ogCUNxXvA8NDAZObZK00ok
     */
}