package com.example.kast.data.source.local

import com.example.kast.KastDb
import com.example.kast.MovieEntity
import com.example.kast.data.model.MovieView

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = KastDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.kastDbQueries

    internal fun insertMovie(movie: MovieView) {
        dbQuery.transaction {
            dbQuery.insertMovie(movie.title, movie.rating.toDouble(), movie.imageUrl)
        }
    }

    internal fun selectAllMovies(): List<MovieEntity> {
        return dbQuery.selectAllMovies().executeAsList()
    }

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.deleteAllMovies()
        }
    }
}

