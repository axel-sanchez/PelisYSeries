package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.domain.UpcomingUseCase

/**
 * View model de [UpcomingFragment]
 * @author Axel Sanchez
 */
class UpcomingViewModel(private val upcomingUseCase: UpcomingUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie>>()

    private fun setListData(listaMovies: List<Movie>) {
        listData.value = listaMovies
    }

    suspend fun getListMovies(repository: GenericRepository) {
        setListData(upcomingUseCase.getMovieList(repository))
    }

    fun getListMoviesLiveData(): LiveData<List<Movie>> {
        return listData
    }
}