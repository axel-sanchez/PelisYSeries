package com.example.pelisyseries.viewmodel

import androidx.lifecycle.*
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.domain.PopularUseCase
import kotlinx.coroutines.launch

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class PopularViewModel(private val popularUseCase: PopularUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie?>>()
    private val listDataFromSearch = MutableLiveData<List<Movie?>>()

    private fun setListData(moviesList: List<Movie?>) {
        listData.value = moviesList
    }

    private fun setListDataFromSearch(moviesList: List<Movie?>) {
        listDataFromSearch.value = moviesList
    }

    fun getListMovies(repository: ProductDao) {
        viewModelScope.launch {
            setListData(popularUseCase.getMovieList(repository))
        }
    }

    fun getListMoviesFromSearch(query: String) {
        viewModelScope.launch {
            setListDataFromSearch(popularUseCase.getMovieListFromSearch(query))
        }
    }

    fun getListMoviesLiveData(): LiveData<List<Movie?>> {
        return listData
    }

    fun getListMoviesLiveDataFromSearch(): LiveData<List<Movie?>> {
        return listDataFromSearch
    }

    class PopularViewModelFactory(private val popularUseCase: PopularUseCase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(PopularUseCase::class.java).newInstance(popularUseCase)
        }
    }
}