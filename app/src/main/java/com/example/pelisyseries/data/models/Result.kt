package com.example.pelisyseries.data.models

/**
 * Clase que almacena la respuesta de la api y que contiene un listado de peliculas
 * @author Axel Sanchez
 */
data class Result(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)