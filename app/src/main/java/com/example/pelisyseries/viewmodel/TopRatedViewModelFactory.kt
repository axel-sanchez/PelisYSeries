package com.example.pelisyseries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pelisyseries.domain.TopRatedUseCase

/**
 * Factory de nuestro [TopRatedViewModel]
 * @author Axel Sanchez
 */
class TopRatedViewModelFactory(private val topRatedUseCase: TopRatedUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TopRatedUseCase::class.java).newInstance(topRatedUseCase)
    }
}