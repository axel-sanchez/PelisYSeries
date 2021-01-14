package com.example.pelisyseries.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.pelisyseries.domain.MainUseCase
import com.example.pelisyseries.ui.adapter.ItemViewPager
import kotlinx.coroutines.launch

/**
 * View model de [MainFragment]
 * @author Axel Sanchez
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    private val listData = MutableLiveData<MutableList<ItemViewPager>>()

    private fun setListData(moviesList: MutableList<ItemViewPager>) {
        listData.value = moviesList
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getListFragments() {
        setListData(mainUseCase.getMovieList())
    }

    fun getListMoviesLiveData(): LiveData<MutableList<ItemViewPager>> {
        return listData
    }

    class MovieViewModelFactory(private val mainUseCase: MainUseCase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(MainUseCase::class.java).newInstance(mainUseCase)
        }
    }
}