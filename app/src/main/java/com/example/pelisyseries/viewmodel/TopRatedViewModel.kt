package com.example.pelisyseries.viewmodel

import androidx.lifecycle.*
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.room.MovieDao
import com.example.pelisyseries.domain.TopRatedUseCase
import kotlinx.coroutines.launch

/**
 * View model de [TopRatedFragment]
 * @author Axel Sanchez
 */
class TopRatedViewModel(private val topRatedUseCase: TopRatedUseCase, private val repository: MovieDao) : ViewModel() {

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

    private fun getListMovies(repository: MovieDao) {
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

    class TopRatedViewModelFactory(private val topRatedUseCase: TopRatedUseCase,
                                   private val repository: MovieDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(TopRatedUseCase::class.java, MovieDao::class.java).newInstance(topRatedUseCase, repository)
        }
    }
}