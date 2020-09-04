package com.example.pelisyseries.domain

import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
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

    /**
     * Obtiene los detalles de la movie
     * @return devuelve una movie
     */
    suspend fun getMovie(repository: GenericRepository, id: Int): Movie {
        var movie = repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ID), arrayOf(id.toString()), null).first()
        movie.keyVideo = api.getVideo(id).value!!.first().key
        return movie
    }
}
