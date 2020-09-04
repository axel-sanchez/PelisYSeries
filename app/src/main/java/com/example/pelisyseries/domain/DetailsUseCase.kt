package com.example.pelisyseries.domain

import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.service.ConnectToApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Caso de uso para el detalle de las peliculas
 * @author Axel Sanchez
 */
class DetailsUseCase: KoinComponent {
    private val api: ConnectToApi by inject()
    private val repository: GenericRepository by inject()

    /**
     * Obtiene los detalles de la movie
     * @return devuelve una movie
     */
    fun getMovie(id: Int): Movie {
        return repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ID), arrayOf(id.toString()), null).first()
    }

    suspend fun getVideo(id: Int): Video? {
        var response = api.getVideo(id)
        return response.value?.let {
            if(it.isNotEmpty()) it.first()
            else null
        }?: kotlin.run { null }
    }
}