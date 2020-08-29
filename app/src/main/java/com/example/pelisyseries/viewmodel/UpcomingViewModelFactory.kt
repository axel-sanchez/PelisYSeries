package com.example.pelisyseries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pelisyseries.domain.UpcomingUseCase

/**
 * Factory de nuestro [UpcomingViewModel]
 * @author Axel Sanchez
 */
class UpcomingViewModelFactory(private val upcomingUseCase: UpcomingUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UpcomingUseCase::class.java).newInstance(upcomingUseCase)
    }
}