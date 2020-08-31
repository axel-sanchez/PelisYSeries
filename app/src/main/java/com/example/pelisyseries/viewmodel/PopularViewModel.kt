package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.domain.PopularUseCase

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class PopularViewModel(private val popularUseCase: PopularUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie>>()
    private val listDataFromSearch = MutableLiveData<List<Movie>>()

    private fun setListData(listaMovies: List<Movie>) {
        listData.value = listaMovies
    }

    private fun setListDataFromSearch(listaMovies: List<Movie>) {
        listDataFromSearch.value = listaMovies
    }

    suspend fun getListMovies(repository: GenericRepository) {
        setListData(popularUseCase.getMovieList(repository))
    }

    suspend fun getListMoviesFromSearch(repository: GenericRepository, query: String) {
        setListDataFromSearch(popularUseCase.getMovieListFromSearch(repository, query))
    }

    fun getListMoviesLiveData(): LiveData<List<Movie>> {
        return listData
    }

    fun getListMoviesLiveDataFromSearch(): LiveData<List<Movie>> {
        return listDataFromSearch
    }
}