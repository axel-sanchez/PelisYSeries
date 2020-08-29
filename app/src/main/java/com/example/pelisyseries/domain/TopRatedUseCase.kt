package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.service.ConnectToApi

/**
 * Caso de uso para las movies mejor calificadas
 * @author Axel Sanchez
 */
class TopRatedUseCase {
    private val api = ConnectToApi.getInstance()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies mejores calificadas
     * @return devuelve un listado de movies mejores calificadas
     */
    suspend fun getMovieList(): List<Movie> {
        var response = api.getTopRated()
        return response.value!!
    }
}
