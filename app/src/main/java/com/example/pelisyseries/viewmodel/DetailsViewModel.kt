package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.domain.DetailsUseCase

/**
 * View model de [DetailFragment]
 * @author Axel Sanchez
 */
class DetailsViewModel(private val detailsUseCase: DetailsUseCase) : ViewModel() {

    private val listData = MutableLiveData<Movie>()

    private fun setListData(movie: Movie) {
        listData.value = movie
    }

    suspend fun getDetailsMovie(repository: GenericRepository, id: Int) {
        setListData(detailsUseCase.getMovie(repository, id))
    }

    fun getDetailsMovieLiveData(): LiveData<Movie> {
        return listData
    }
}