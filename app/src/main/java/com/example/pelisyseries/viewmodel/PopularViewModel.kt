package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.domain.PopularUseCase

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class PopularViewModel(private val popularUseCase: PopularUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie>>()

    private fun setListData(listaMovies: List<Movie>) {
        listData.value = listaMovies
    }

    suspend fun getListMovies() {
        setListData(popularUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<List<Movie>> {
        return listData
    }
}