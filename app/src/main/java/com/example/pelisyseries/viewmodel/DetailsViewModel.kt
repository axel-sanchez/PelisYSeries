package com.example.pelisyseries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.domain.DetailsUseCase
import java.lang.Exception

/**
 * View model de [DetailFragment]
 * @author Axel Sanchez
 */
class DetailsViewModel(private val detailsUseCase: DetailsUseCase) : ViewModel() {

    private val listData = MutableLiveData<Movie?>()
    private val listDataVideo = MutableLiveData<Video?>()

    private fun setListData(movie: Movie?) {
        listData.value = movie
    }

    private fun setListDataVideo(video: Video?) {
        listDataVideo.value = video
    }

    fun getDetailsMovie(id: Int) {
        setListData(detailsUseCase.getMovie(id))
    }

    suspend fun getVideo(id: Int) {
        setListDataVideo(detailsUseCase.getVideo(id))
    }

    fun getDetailsMovieLiveData(): LiveData<Movie?> {
        return listData
    }

    fun getLiveDataVideo(): LiveData<Video?> {
        return listDataVideo
    }
}