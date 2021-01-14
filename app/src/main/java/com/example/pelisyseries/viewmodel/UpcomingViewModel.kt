package com.example.pelisyseries.viewmodel

import androidx.lifecycle.*
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.room.ProductDao
import com.example.pelisyseries.domain.UpcomingUseCase
import kotlinx.coroutines.launch

/**
 * View model de [UpcomingFragment]
 * @author Axel Sanchez
 */
class UpcomingViewModel(private val upcomingUseCase: UpcomingUseCase, private val repository: ProductDao) : ViewModel() {

    private val listData: MutableLiveData<List<Movie?>> by lazy {
        MutableLiveData<List<Movie?>>().also {
            getListMovies(repository)
        }
    }
    private val listDataFromSearch = MutableLiveData<List<Movie?>>()

    private fun setListData(moviesList: List<Movie?>) {
        listData.value = moviesList
    }

    private fun setListDataFromSearch(moviesList: List<Movie?>) {
        listDataFromSearch.value = moviesList
    }

    private fun getListMovies(repository: ProductDao) {
        viewModelScope.launch {
            setListData(upcomingUseCase.getMovieList(repository))
        }
    }

    fun getListMoviesFromSearch(query: String) {
        viewModelScope.launch {
            setListDataFromSearch(upcomingUseCase.getMovieListFromSearch(query))
        }
    }

    fun getListMoviesLiveData(): LiveData<List<Movie?>> {
        return listData
    }

    fun getListMoviesLiveDataFromSearch(): LiveData<List<Movie?>> {
        return listDataFromSearch
    }

    class UpcomingViewModelFactory(private val upcomingUseCase: UpcomingUseCase, private val repository: ProductDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(UpcomingUseCase::class.java, ProductDao::class.java).newInstance(upcomingUseCase, repository)
        }
    }
}