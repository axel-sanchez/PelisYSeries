package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.data.service.ConnectToApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Caso de uso para el detalle de las peliculas
 * @author Axel Sanchez
 */
class DetailsUseCase: KoinComponent {
    private val api: ConnectToApi by inject()
    private val repository: ProductDao by inject()

    /**
     * Obtiene los detalles de la movie
     * @return devuelve una movie
     */
    suspend fun getMovie(id: Long): Movie? {
        var movies = repository.getMovieById(id)
        return if(movies.isNotEmpty()) movies.first()
        else null
    }

    suspend fun getVideo(id: Long): Video? {
        var response = api.getVideo(id)
        return response.value?.let {
            if(it.isNotEmpty()) it.first()
            else null
        }?: kotlin.run { null }
    }
}