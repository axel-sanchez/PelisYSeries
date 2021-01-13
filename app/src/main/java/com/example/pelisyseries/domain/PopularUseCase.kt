package com.example.pelisyseries.domain

import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.POPULAR
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.data.service.ConnectToApi
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Caso de uso para las movies populares
 * @author Axel Sanchez
 */
class PopularUseCase: KoinComponent {
    private val api: ConnectToApi by inject()

    /**
     * Recibe el mutableLiveData y obtiene su listado de movies populares
     * @param [repository] objeto que sirve para hacer llamadas a la database
     * @return devuelve un listado de movies populares
     */
    suspend fun getMovieList(repository: ProductDao): List<Movie?> {

        var movies = repository.getMovieByOrigin(POPULAR)

        return if(movies.isEmpty()){
            var response = api.getPopular()
            response.value?.let { it }?: kotlin.run { listOf() }
        } else{
            movies
        }
    }

    /**
     * Obtiene las peliculas que corresponden a la búsqueda
     * @param [query] nombre de la pelicula
     * @return devuelve un listado de movies encontradas
     */
    suspend fun getMovieListFromSearch(query: String): List<Movie?> {
        var response = api.search(query)
        return response.value?.let { it }?: kotlin.run { listOf() }
    }
}