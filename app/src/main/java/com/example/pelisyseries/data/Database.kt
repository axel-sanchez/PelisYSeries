package com.example.pelisyseries.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class Database(context: Context): SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        // If you change the database schema, you must increment the database version.
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "moviesDB.db3"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Create Table
        db!!.execSQL(SQL_CREATE_MOVIE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //delete table
        db!!.execSQL(SQL_DELETE_MOVIE)
        //create table
        onCreate(db)
    }
}

//CREATE
private const val  SQL_CREATE_MOVIE =
    "CREATE TABLE ${TableMovie.Columns.TABLE_NAME} (" +
            "${TableMovie.Columns.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
            "${TableMovie.Columns.COLUMN_NAME_IS_ADULT} BOOL," +
            "${TableMovie.Columns.COLUMN_NAME_BACKFROP_PATH} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_GENRE_ID} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_ORIGINAL_LANGAUGE} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_ORIGINAL_TITLE} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_OVERVIEW} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_POPULARITY} DOUBLE," +
            "${TableMovie.Columns.COLUMN_NAME_POSTER_PATH} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_RELEASE_DATE} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_TITLE} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_VIDEO} BOOL," +
            "${TableMovie.Columns.COLUMN_NAME_VOTE_AVERAGE} DOUBLE," +
            "${TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST} TEXT," +
            "${TableMovie.Columns.COLUMN_NAME_VOTE_COUNT} INT)"

//DELETE
private const val SQL_DELETE_MOVIE          = "DROP TABLE IF EXISTS ${TableMovie.Columns.TABLE_NAME}"

//TABLE
object TableMovie{
    // Table contents are grouped together in an anonymous object.
    object Columns : BaseColumns {
        const val TABLE_NAME                       =    "movie"
        const val COLUMN_NAME_ID                   =    "id"
        const val COLUMN_NAME_IS_ADULT             =    "is_adult"
        const val COLUMN_NAME_BACKFROP_PATH        =    "backdrop_path"
        const val COLUMN_NAME_GENRE_ID             =    "genre_ids"
        const val COLUMN_NAME_ORIGINAL_LANGAUGE    =    "original_language"
        const val COLUMN_NAME_ORIGINAL_TITLE       =    "original_title"
        const val COLUMN_NAME_OVERVIEW             =    "overview"
        const val COLUMN_NAME_POPULARITY           =    "popularity"
        const val COLUMN_NAME_POSTER_PATH          =    "poster_path"
        const val COLUMN_NAME_RELEASE_DATE         =    "release_date"
        const val COLUMN_NAME_TITLE                =    "title"
        const val COLUMN_NAME_VIDEO                =    "video"
        const val COLUMN_NAME_VOTE_AVERAGE         =    "vote_average"
        const val COLUMN_NAME_VOTE_COUNT           =    "vote_count"
        const val COLUMN_NAME_ORIGEN_LIST          =    "origen_list"
    }
}