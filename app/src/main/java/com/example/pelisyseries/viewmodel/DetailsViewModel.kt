package com.example.pelisyseries.viewmodel

import androidx.lifecycle.*
import com.example.pelisyseries.data.models.Movie
import com.example.pelisyseries.data.models.Video
import com.example.pelisyseries.domain.DetailsUseCase
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            setListData(detailsUseCase.getMovie(id))
        }
    }

    fun getVideo(id: Int) {
        viewModelScope.launch {
            setListDataVideo(detailsUseCase.getVideo(id))
        }
    }

    fun getDetailsMovieLiveData(): LiveData<Movie?> {
        return listData
    }

    fun getLiveDataVideo(): LiveData<Video?> {
        return listDataVideo
    }

    class DetailsViewModelFactory(private val detailsUseCase: DetailsUseCase): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(DetailsUseCase::class.java).newInstance(detailsUseCase)
        }
    }
}