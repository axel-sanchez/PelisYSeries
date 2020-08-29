package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.domain.MovieUseCase

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie>>()

    private fun setListData(listaMovies: List<Movie>) {
        listData.value = listaMovies
    }

    suspend fun getListMovies() {
        setListData(movieUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<List<Movie>> {
        return listData
    }
}