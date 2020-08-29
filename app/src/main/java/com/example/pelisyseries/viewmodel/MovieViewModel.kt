package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.domain.MovieUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val listData = MutableLiveData<MutableList<ItemViewPager>>()

    private fun setListData(listaMovies: MutableList<ItemViewPager>) {
        listData.value = listaMovies
    }

    fun getListFragments() {
        setListData(movieUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<MutableList<ItemViewPager>> {
        return listData
    }
}