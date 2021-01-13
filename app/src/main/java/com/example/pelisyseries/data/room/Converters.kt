package com.example.pelisyseries.data.room

import android.widget.ImageView
import androidx.room.TypeConverter
import com.example.pelisyseries.data.models.Movie
import com.google.gson.Gson
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/** Convertidor de clases para la base de datos con room
 * @author Axel Sanchez
 */
class Converters: KoinComponent {
    private val gson: Gson by inject()

    @TypeConverter
    fun fromMovie(movie: Movie?): String? {
        movie?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toMovie(movieString: String?): Movie? {
        movieString?.let {
            return gson.fromJson(it, Movie::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromImageView(imageView: ImageView?): String? {
        imageView?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toImageView(imageViewString: String?): ImageView? {
        imageViewString?.let {
            return gson.fromJson(it, ImageView::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int?>?): String? {
        var response = ""
        genreIds?.let {
            for (i in genreIds.indices) {
                response += if (i == 0) genreIds[i]
                else ";${genreIds[i]}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toGenreIds(concat: String?): List<Int?>? {
        val list = concat?.split(";")
        list?.let {
            return it.map { str -> if (str != "null") str.toInt() else null }
        } ?: return null
    }
}