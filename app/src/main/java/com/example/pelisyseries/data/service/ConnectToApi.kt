package com.example.pelisyseries.data.service

import androidx.lifecycle.MutableLiveData
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.UPCOMING
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.data.room.ProductDao
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "a0de5a9fe43359e41cb94081d6bafc05" //(AUTH V3)

/**
 * Esta clase es la encargada de conectarse a las api's
 * @author Axel Sanchez
 */
class ConnectToApi: KoinComponent {
    private val service: ApiService by inject()
    private val repository: ProductDao by inject()

    /**
     * Esta función es la encargada de retornar las movies mas populares
     * @return devuelve un mutableLiveData que contiene un listado de [Movie] populares
     */
    suspend fun getPopular(): MutableLiveData<List<Movie?>> {
        var mutableLiveData = MutableLiveData<List<Movie?>>()
        var response = service.getPopular(API_KEY)
        if(response.isSuccessful) mutableLiveData.value = response.body()?.let { it.results }?: kotlin.run { listOf() }
        else mutableLiveData.value = listOf()
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar las movies mejor calificadas
     * @return devuelve un mutableLiveData que contiene un listado de [Movie] mejor calificadas
     */
    suspend fun getTopRated(): MutableLiveData<List<Movie?>> {
        var mutableLiveData = MutableLiveData<List<Movie?>>()
        var response = service.getTopRated(API_KEY)
        if(response.isSuccessful) mutableLiveData.value = response.body()?.let { it.results }?: kotlin.run { listOf() }
        else mutableLiveData.value = listOf()
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar las movies próximas a estrenar
     * @return devuelve un mutableLiveData que contiene un listado de [Movie] próximas a estrenar
     */
    suspend fun getUpcoming(): MutableLiveData<List<Movie?>> {
        var mutableLiveData = MutableLiveData<List<Movie?>>()
        var response = service.getUpcoming(API_KEY)

        response.body()?.let {
            for(movie in it.results){
                movie.origen = UPCOMING
                repository.insertMovie(movie)
            }
        }

        if(response.isSuccessful) mutableLiveData.value = response.body()?.let { it.results }?: kotlin.run { listOf() }
        else mutableLiveData.value = listOf()
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar las movies que coinciden con la búsqueda
     * @param [query] nombre de la pelicula
     * @return devuelve un mutableLiveData que contiene un listado de [Movie] que coinciden con la búsqueda
     */
    suspend fun search(query: String): MutableLiveData<List<Movie?>> {
        var mutableLiveData = MutableLiveData<List<Movie?>>()
        var response = service.search(API_KEY, query)
        if(response.isSuccessful) mutableLiveData.value = response.body()?.let { it.results }?: kotlin.run { listOf() }
        else mutableLiveData.value = listOf()
        return mutableLiveData
    }

    /**
     * Esta función es la encargada de retornar el vídeo de una pelicula
     * @param [id] id de la pelicula
     * @return devuelve un mutableLiveData que contiene un listado de [Video]
     */
    suspend fun getVideo(id: Long): MutableLiveData<List<Video?>> {
        var mutableLiveData = MutableLiveData<List<Video?>>()
        var response = service.getVideo(id.toString(), API_KEY)
        if(response.isSuccessful) mutableLiveData.value = response.body()?.let{ it.results }?: kotlin.run { listOf() }
        else mutableLiveData.value = listOf()
        return mutableLiveData
    }
}