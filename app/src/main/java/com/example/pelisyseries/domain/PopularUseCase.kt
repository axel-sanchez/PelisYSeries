package com.example.pelisyseries.domain

import android.app.Application
import android.content.Context
import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.repository.POPULAR
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
    suspend fun getMovieList(repository: GenericRepository): List<Movie> {

        var movies = repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST), arrayOf(POPULAR), null)

        return if(movies.isEmpty()){
            var response = api.getPopular()
            response.value!!
        } else{
            movies
        }
    }
}
