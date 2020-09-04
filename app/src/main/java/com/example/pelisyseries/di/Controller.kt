package com.example.pelisyseries.di

import com.example.pelisyseries.data.Database
import com.example.pelisyseries.data.repository.GenericRepository
import com.example.pelisyseries.data.service.ApiService
import com.example.pelisyseries.data.service.BASE_URL
import com.example.pelisyseries.data.service.ConnectToApi
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
    single { Database(androidContext()) }
    single { GenericRepository() }
}