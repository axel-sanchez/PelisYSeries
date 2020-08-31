package com.example.pelisyseries.domain

import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.repository.POPULAR
import com.example.pelisyseries.data.repository.TOP_RATED
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
    suspend fun getMovieList(repository: GenericRepository): List<Movie> {
        var movies = repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST), arrayOf(TOP_RATED), null)

        return if (movies.isEmpty()) {
            var response = api.getTopRated()
            response.value!!
        } else {
            movies
        }
    }

    suspend fun getMovieListFromSearch(repository: GenericRepository, query: String): List<Movie> {
        var response = api.search(query)
        return response.value!!
    }
}
