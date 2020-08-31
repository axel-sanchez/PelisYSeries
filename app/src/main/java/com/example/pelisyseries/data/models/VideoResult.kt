package com.example.pelisyseries.data.models

/**
 * Clase que almacena la respuesta de la api [getVideo]
 * @author Axel Sanchez
 */
data class VideoResult(
    val id: Int,
    val results: List<Video>
)