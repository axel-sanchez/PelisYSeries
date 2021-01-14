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
class TopRatedViewModel(private val topRatedUseCase: TopRatedUseCase, private val repository: ProductDao) : ViewModel() {

    private var query = ""

    private val listData: MutableLiveData<List<Movie?>> by lazy {
        MutableLiveData<List<Movie?>>().also {
            getListMovies(repository)
        }
    }
    private val listDataFromSearch: MutableLiveData<List<Movie?>> by lazy {
        MutableLiveData<List<Movie?>>().also {
            getListMoviesFromSearch(query)
        }
    }

    private fun setListData(moviesList: List<Movie?>) {
        listData.value = moviesList
    }

    private fun setListDataFromSearch(moviesList: List<Movie?>) {
        listDataFromSearch.value = moviesList
    }

    private fun getListMovies(repository: ProductDao) {
        viewModelScope.launch {
            setListData(topRatedUseCase.getMovieList(repository))
        }
    }

    private fun getListMoviesFromSearch(query: String) {
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

    fun changeQuery(newQuery: String){
        query = newQuery
        getListMoviesFromSearch(query)
    }

    class TopRatedViewModelFactory(private val topRatedUseCase: TopRatedUseCase,
                                   private val repository: ProductDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(TopRatedUseCase::class.java, ProductDao::class.java).newInstance(topRatedUseCase, repository)
        }
    }
}