package com.example.pelisyseries.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pelisyseries.data.models.Movie

/**
 * Base de datos utilizando room
 * @author Axel Sanchez
 */
@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun productDao(): MovieDao
}