package com.example.pelisyseries.di

import androidx.room.Room
import com.example.pelisyseries.data.room.Database
import com.example.pelisyseries.data.service.ApiService
import com.example.pelisyseries.data.service.BASE_URL
import com.example.pelisyseries.data.service.ConnectToApi
import com.example.pelisyseries.domain.MovieUseCase
import com.example.pelisyseries.domain.PopularUseCase
import com.example.pelisyseries.domain.TopRatedUseCase
import com.example.pelisyseries.domain.UpcomingUseCase
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Este es el módulo encargado de crear todas las inyecciones que uso en toda la app
 * @author Axel Sanchez
 */
val moduleApp = module{
    single{ Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }
    single { (get() as Retrofit).create(ApiService::class.java) }
    single {  ConnectToApi() }

    single { Room
        .databaseBuilder(androidContext(), Database::class.java, "moviesAndSeriesDB.db3")
        .build() }

    single { Gson() }

    single { (get() as Database).productDao() }

    single { PopularUseCase() }
    single { TopRatedUseCase() }
    single { UpcomingUseCase() }
    single { MovieUseCase() }
}