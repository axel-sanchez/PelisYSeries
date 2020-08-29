package com.example.pelisyseries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pelisyseries.domain.MovieUseCase

/**
 * Factory de nuestro [MovieViewModel]
 * @author Axel Sanchez
 */
class MyViewModelFactory(private val movieUseCase: MovieUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieUseCase::class.java).newInstance(movieUseCase)
    }
}