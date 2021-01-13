package com.example.pelisyseries.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pelisyseries.domain.MovieUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val listData = MutableLiveData<MutableList<ItemViewPager>>()

    private fun setListData(moviesList: MutableList<ItemViewPager>) {
        listData.value = moviesList
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getListFragments() {
        setListData(movieUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<MutableList<ItemViewPager>> {
        return listData
    }

    class MovieViewModelFactory(private val movieUseCase: MovieUseCase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(MovieUseCase::class.java).newInstance(movieUseCase)
        }
    }
}