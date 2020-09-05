package com.example.pelisyseries.data.models

import android.widget.ImageView

/**
 * Clase que almacena las peliculas que se encuentran en [Result]
 * @author Axel Sanchez
 */
data class Movie(
    val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    var origen: String? = null,
    var imageView: ImageView? = null,
    var keyVideo: String? = null
)