package com.example.pelisyseries.data.room

import androidx.room.*
import com.example.pelisyseries.data.models.Movie

/**
 * @author Axel Sanchez
 */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: Movie)

    @Query("Delete from Movie where id = :idMovie")
    suspend fun deleteMovie(idMovie: Long)

    @Query("SELECT * FROM Movie where origen = :origin")
    suspend fun getMovieByOrigin(origin: String): List<Movie>

    @Query("SELECT * FROM Movie where id = :idMovie")
    suspend fun getMovieById(idMovie: Long): List<Movie>
}