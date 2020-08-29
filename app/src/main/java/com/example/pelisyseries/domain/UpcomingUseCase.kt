package com.example.pelisyseries.domain

import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.repository.TOP_RATED
import com.example.pelisyseries.data.repository.UPCOMING
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
    suspend fun getMovieList(repository: GenericRepository): List<Movie> {

        var movies = repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST), arrayOf(UPCOMING), null)

        return if (movies.isEmpty()) {
            var response = api.getUpcoming()
            response.value!!
        } else {
            movies
        }
    }
}