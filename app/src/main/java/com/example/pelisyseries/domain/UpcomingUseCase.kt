package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.UPCOMING
import com.example.pelisyseries.data.room.MovieDao
import com.example.pelisyseries.data.service.ConnectToApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Caso de uso para las movies próximas a estrenar
 * @author Axel Sanchez
 */
class UpcomingUseCase: KoinComponent {
    private val api: ConnectToApi by inject()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies próximas a estrenar
     * @param [repository] objeto que manipula la database
     * @return devuelve un listado de movies próximas a estrenar
     */
    suspend fun getMovieList(repository: MovieDao): List<Movie?> {

        var movies = repository.getMovieByOrigin(UPCOMING)

        return if (movies.isEmpty()) {
            var response = api.getUpcoming()
            response.value?.let { it }?: kotlin.run { listOf() }
        } else {
            movies
        }
    }
    /**
     * Obtiene las peliculas que corresponden con la búsqueda
     * @param [query] nombre de la pelicula
     * @return devuelve un listado de movies que corresponden con la búsqueda
     */
    suspend fun getMovieListFromSearch(query: String): List<Movie?> {
        var response = api.search(query)
        return response.value?.let { it }?: kotlin.run { listOf() }
    }
}