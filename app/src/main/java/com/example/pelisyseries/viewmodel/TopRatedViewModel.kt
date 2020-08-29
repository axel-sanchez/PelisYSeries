package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.domain.TopRatedUseCase

/**
 * View model de [TopRatedFragment]
 * @author Axel Sanchez
 */
class TopRatedViewModel(private val topRatedUseCase: TopRatedUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie>>()

    private fun setListData(listaMovies: List<Movie>) {
        listData.value = listaMovies
    }

    suspend fun getListMovies() {
        setListData(topRatedUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<List<Movie>> {
        return listData
    }
}