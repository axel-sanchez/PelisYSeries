package com.example.pelisyseries.viewmodel

import androidx.lifecycle.*
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.domain.TopRatedUseCase
import kotlinx.coroutines.launch

/**
 * View model de [TopRatedFragment]
 * @author Axel Sanchez
 */
class TopRatedViewModel(private val topRatedUseCase: TopRatedUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Movie?>>()
    private val listDataFromSearch = MutableLiveData<List<Movie?>>()

    private fun setListData(listaMovies: List<Movie?>) {
        listData.value = listaMovies
    }

    private fun setListDataFromSearch(listaMovies: List<Movie?>) {
        listDataFromSearch.value = listaMovies
    }

    fun getListMovies(repository: ProductDao) {
        viewModelScope.launch {
            setListData(topRatedUseCase.getMovieList(repository))
        }
    }

    fun getListMoviesFromSearch(query: String) {
        viewModelScope.launch {
            setListDataFromSearch(topRatedUseCase.getMovieListFromSearch(query))
        }
    }

    fun getListMoviesLiveData(): LiveData<List<Movie?>> {
        return listData
    }

    fun getListMoviesLiveDataFromSearch(): LiveData<List<Movie?>> {
        return listDataFromSearch
    }

    class TopRatedViewModelFactory(private val topRatedUseCase: TopRatedUseCase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(TopRatedUseCase::class.java).newInstance(topRatedUseCase)
        }
    }
}