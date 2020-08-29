package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.service.ConnectToApi

/**
 * Caso de uso para las movies populares
 * @author Axel Sanchez
 */
class PopularUseCase {
    private val api = ConnectToApi.getInstance()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies populares
     * @return devuelve un listado de movies populares
     */
    suspend fun getMovieList(): List<Movie> {
        var response = api.getPopular()
        return response.value!!
    }
}
