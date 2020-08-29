package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.service.ConnectToApi

/**
 * Caso de uso para las movies
 * @author Axel Sanchez
 */
class MovieUseCase {
    private val api = ConnectToApi.getInstance()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies
     * @return devuelve un listado de movies
     */
    suspend fun getMovieList(): List<Movie> {
        var response = api.getPopular()
        return response.value!!
    }
}
