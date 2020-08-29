package com.example.pelisyseries.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.pelisyseries.data.Database
import com.example.pelisyseries.data.TableMovie
import com.example.pelisyseries.data.models.Movie

class GenericRepository {

    private lateinit var dbHelper: Database
    lateinit var db: SQLiteDatabase

    companion object {
        //Utilizo el patrón singleton
        private var instance: GenericRepository? = null

        fun getInstance(context: Context): GenericRepository {
            if (instance == null) {
                instance = GenericRepository()
                instance!!.dbHelper = Database(context)
                instance!!.db = instance!!.dbHelper.writableDatabase
            }
            return instance!!
        }
    }

    //Insertamos una movie
    fun insert(item: Movie): Long {
        return try {
            var idsGenre = ""
            for (id in item.genre_ids) {
                if (idsGenre.isEmpty()) idsGenre += id
                else idsGenre += ";$id"
            }
            val values = ContentValues().apply {
                put(TableMovie.Columns.COLUMN_NAME_ID, item.id)
                put(TableMovie.Columns.COLUMN_NAME_IS_ADULT, item.adult)
                put(TableMovie.Columns.COLUMN_NAME_BACKFROP_PATH, item.backdrop_path)
                put(TableMovie.Columns.COLUMN_NAME_GENRE_ID, idsGenre)
                put(TableMovie.Columns.COLUMN_NAME_ORIGINAL_LANGAUGE, item.original_language)
                put(TableMovie.Columns.COLUMN_NAME_ORIGINAL_TITLE, item.original_title)
                put(TableMovie.Columns.COLUMN_NAME_OVERVIEW, item.overview)
                put(TableMovie.Columns.COLUMN_NAME_POPULARITY, item.popularity)
                put(TableMovie.Columns.COLUMN_NAME_POSTER_PATH, item.poster_path)
                put(TableMovie.Columns.COLUMN_NAME_RELEASE_DATE, item.release_date)
                put(TableMovie.Columns.COLUMN_NAME_TITLE, item.title)
                put(TableMovie.Columns.COLUMN_NAME_VIDEO, item.video)
                put(TableMovie.Columns.COLUMN_NAME_VOTE_AVERAGE, item.vote_average)
                put(TableMovie.Columns.COLUMN_NAME_VOTE_COUNT, item.vote_count)
                put(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST, item.origen)
            }
            db.insertWithOnConflict(TableMovie.Columns.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        } catch (e: Exception) {
            Log.e("INSERT MOVIE", e.message!!)
            -1
        }
    }

    fun update(item: Movie): Int {
        val db = dbHelper.writableDatabase

        var idsGenre = ""
        for (id in item.genre_ids) {
            if (idsGenre.isEmpty()) idsGenre += id
            else idsGenre += ";$id"
        }

        val values = ContentValues().apply {
            put(TableMovie.Columns.COLUMN_NAME_ID, item.id)
            put(TableMovie.Columns.COLUMN_NAME_IS_ADULT, item.adult)
            put(TableMovie.Columns.COLUMN_NAME_BACKFROP_PATH, item.backdrop_path)
            put(TableMovie.Columns.COLUMN_NAME_GENRE_ID, idsGenre)
            put(TableMovie.Columns.COLUMN_NAME_ORIGINAL_LANGAUGE, item.original_language)
            put(TableMovie.Columns.COLUMN_NAME_ORIGINAL_TITLE, item.original_title)
            put(TableMovie.Columns.COLUMN_NAME_OVERVIEW, item.overview)
            put(TableMovie.Columns.COLUMN_NAME_POPULARITY, item.popularity)
            put(TableMovie.Columns.COLUMN_NAME_POSTER_PATH, item.poster_path)
            put(TableMovie.Columns.COLUMN_NAME_RELEASE_DATE, item.release_date)
            put(TableMovie.Columns.COLUMN_NAME_TITLE, item.title)
            put(TableMovie.Columns.COLUMN_NAME_VIDEO, item.video)
            put(TableMovie.Columns.COLUMN_NAME_VOTE_AVERAGE, item.vote_average)
            put(TableMovie.Columns.COLUMN_NAME_VOTE_COUNT, item.vote_count)
            put(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST, item.origen)
        }

        // Which row to update, based on the title
        val selection = "${TableMovie.Columns.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(item.id.toString())

        return db.updateWithOnConflict(TableMovie.Columns.TABLE_NAME, values, selection, selectionArgs, SQLiteDatabase.CONFLICT_REPLACE)
    }


    fun getMovie(whereColumns: Array<String>?, whereArgs: Array<String>?, orderByColumn: String?): MutableList<Movie> {
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            TableMovie.Columns.COLUMN_NAME_ID,
            TableMovie.Columns.COLUMN_NAME_IS_ADULT,
            TableMovie.Columns.COLUMN_NAME_BACKFROP_PATH,
            TableMovie.Columns.COLUMN_NAME_GENRE_ID,
            TableMovie.Columns.COLUMN_NAME_ORIGINAL_LANGAUGE,
            TableMovie.Columns.COLUMN_NAME_ORIGINAL_TITLE,
            TableMovie.Columns.COLUMN_NAME_OVERVIEW,
            TableMovie.Columns.COLUMN_NAME_POPULARITY,
            TableMovie.Columns.COLUMN_NAME_POSTER_PATH,
            TableMovie.Columns.COLUMN_NAME_RELEASE_DATE,
            TableMovie.Columns.COLUMN_NAME_TITLE,
            TableMovie.Columns.COLUMN_NAME_VIDEO,
            TableMovie.Columns.COLUMN_NAME_VOTE_AVERAGE,
            TableMovie.Columns.COLUMN_NAME_VOTE_COUNT,
            TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST
        )

        // Filter results WHERE "title" = 'My Title'
        val selection = setWhere(whereColumns)

        // How you want the results sorted in the resulting Cursor
        val sortOrder: String? = if (orderByColumn?.count() != 0) "$orderByColumn DESC" else null

        var cursor = db.query(
            TableMovie.Columns.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            whereArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        var idsGenre: List<String>

        val items = mutableListOf<Movie>()
        while (cursor.moveToNext()) {
            idsGenre = cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_GENRE_ID)).split(";")
            items.add(
                Movie(
                    cursor.getInt(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_ID)),
                    cursor.getInt(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_IS_ADULT)) != 0,
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_BACKFROP_PATH)),
                    idsGenre.map { id -> id.toInt() },
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_ORIGINAL_LANGAUGE)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_ORIGINAL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_OVERVIEW)),
                    cursor.getDouble(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_POPULARITY)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_TITLE)),
                    cursor.getInt(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_VIDEO)) != 0,
                    cursor.getDouble(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_VOTE_AVERAGE)),
                    cursor.getInt(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_VOTE_COUNT)),
                    cursor.getString(cursor.getColumnIndex(TableMovie.Columns.COLUMN_NAME_ORIGEN_LIST))
                )
            )
        }
        return items
    }

    fun deleteMovie(id: Long): Int {
        val db = dbHelper.writableDatabase
        // Define 'where' part of query.
        val selection = "${TableMovie.Columns.COLUMN_NAME_ID} = ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id.toString())
        // Issue SQL statement.
        return db.delete(TableMovie.Columns.TABLE_NAME, selection, selectionArgs)
    }

    private fun setWhere(whereColumns: Array<String>?): String? {
        if (whereColumns != null) {
            val result = StringBuilder()
            var first = true
            for (w in whereColumns) {
                if (first)
                    first = false
                else
                    result.append(" AND ")

                result.append(w)
                result.append("=?")
            }
            return result.toString()
        } else {
            return null
        }
    }
}