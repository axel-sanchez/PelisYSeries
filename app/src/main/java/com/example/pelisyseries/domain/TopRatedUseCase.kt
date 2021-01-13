package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.TOP_RATED
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.data.service.ConnectToApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Caso de uso para las movies mejor calificadas
 * @author Axel Sanchez
 */
class TopRatedUseCase: KoinComponent {
    private val api: ConnectToApi by inject()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies mejores calificadas
     * @param [repository] objeto que manipula la database
     * @return devuelve un listado de movies mejores calificadas
     */
    suspend fun getMovieList(repository: ProductDao): List<Movie?> {
        var movies = repository.getMovieByOrigin(TOP_RATED)

        return if (movies.isEmpty()) {
            var response = api.getTopRated()
            response.value?.let { it }?: kotlin.run { listOf() }
        } else {
            movies
        }
    }

    /**
     * Obtiene las peliculas que corresponden con la búsqueda
     * @param [query] nombre de la pelicula
     * @return devuelve un listado de movies que coinciden con la búsqueda
     */
    suspend fun getMovieListFromSearch(query: String): List<Movie?> {
        var response = api.search(query)
        return response.value?.let { it }?: kotlin.run { listOf() }
    }
}
