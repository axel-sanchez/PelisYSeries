package com.example.pelisyseries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pelisyseries.domain.PopularUseCase

/**
 * Factory de nuestro [PopularViewModel]
 * @author Axel Sanchez
 */
class PopularViewModelFactory(private val popularUseCase: PopularUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PopularUseCase::class.java).newInstance(popularUseCase)
    }
}