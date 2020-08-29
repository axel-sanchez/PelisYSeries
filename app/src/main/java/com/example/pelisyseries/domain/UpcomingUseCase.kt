package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.service.ConnectToApi

/**
 * Caso de uso para las movies próximas a estrenar
 * @author Axel Sanchez
 */
class UpcomingUseCase {
    private val api = ConnectToApi.getInstance()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies próximas a estrenar
     * @return devuelve un listado de movies próximas a estrenar
     */
    suspend fun getMovieList(): List<Movie> {
        var response = api.getUpcoming()
        return response.value!!
    }
}