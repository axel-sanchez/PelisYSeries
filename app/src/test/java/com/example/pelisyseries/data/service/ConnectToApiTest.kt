package com.example.pelisyseries.data.service

import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectToApiTest {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service: ApiService = retrofit.create(ApiService::class.java)

    @Test fun getPopularSuccessfully() {
        assertTrue(service.getPopularTest(API_KEY).execute().isSuccessful)
    }

    @Test
    fun getTopRatedSuccessfully() {
        assertTrue(service.getTopRatedTest(API_KEY).execute().isSuccessful)
    }

    @Test
    fun getUpcomingSuccessfully() {
        assertTrue(service.getUpcomingTest(API_KEY).execute().isSuccessful)
    }

    @Test
    fun searchSuccessfully() {
        assertTrue(service.searchTest(API_KEY, "Iron Man").execute().isSuccessful)
    }

    @Test
    fun getVideoSuccessfully() {
        assertTrue(service.getVideoTest("475557", API_KEY).execute().isSuccessful)
    }

    @Test fun errorAuthentication(){
        assertFalse(service.getPopularTest(API_KEY+"sadsa").execute().isSuccessful)
        assertFalse(service.getTopRatedTest(API_KEY+"asdas").execute().isSuccessful)
        assertFalse(service.getUpcomingTest(API_KEY+"asdas").execute().isSuccessful)
        assertFalse(service.searchTest(API_KEY+"sadsa", "Iron Man").execute().isSuccessful)
        assertFalse(service.getVideoTest("475557", API_KEY+"asdasd").execute().isSuccessful)
    }

    @Test fun notFound() {
        assertFalse(service.getVideoTest("-11", API_KEY).execute().isSuccessful)
    }
}