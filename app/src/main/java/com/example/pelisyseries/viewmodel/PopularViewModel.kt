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
class PopularViewModel(private val popularUseCase: PopularUseCase, private val repository: ProductDao) : ViewModel() {

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
            setListData(popularUseCase.getMovieList(repository))
        }
    }

    private fun getListMoviesFromSearch(query: String) {
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

    fun changeQuery(newQuery: String){
        query = newQuery
        getListMoviesFromSearch(query)
    }

    class PopularViewModelFactory(private val popularUseCase: PopularUseCase,
                                  private val repository: ProductDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(PopularUseCase::class.java, ProductDao::class.java).newInstance(popularUseCase, repository)
        }
    }
}