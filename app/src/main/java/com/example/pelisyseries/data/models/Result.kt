package com.example.pelisyseries.data.models

data class Result(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)