package com.example.pelisyseries.domain

import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.service.ConnectToApi

/**
 * Caso de uso para las movies populares
 * @author Axel Sanchez
 */
class DetailsUseCase {
    private val api = ConnectToApi.getInstance()

    /**
     * Obtiene los detalles de la movie
     * @return devuelve una movie
     */
    fun getMovie(repository: GenericRepository, id: Int): Movie {
        return repository.getMovie(arrayOf(TableMovie.Columns.COLUMN_NAME_ID), arrayOf(id.toString()), null).first()
    }
}
