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

    suspend fun getMovieList(repository: ProductDao): List<Movie?> {
        var movies = repository.getMovieByOrigin(TOP_RATED)

        return if (movies.isEmpty()) {
            var response = api.getTopRated()
            response.value?.let { it }?: kotlin.run { listOf() }
        } else {
            movies
        }
    }

    suspend fun getMovieListFromSearch(query: String): List<Movie?> {
        var response = api.search(query)
        return response.value?.let { it }?: kotlin.run { listOf() }
    }
}
