package com.example.kast.data.source.local

import com.example.kast.KastDb
import com.example.kast.MovieEntity
import com.example.kast.data.model.MovieView
import com.squareup.sqldelight.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.flatMapConcat

internal class Database(databaseDriverFactory: DatabaseDriverFactory) : MoviesDao {
    private val database = KastDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.kastDbQueries

    override suspend fun insertMovie(movie: MovieEntity) {
        withContext(Dispatchers.Default) {
            dbQuery.insertMovie(movie.id, movie.title, movie.rating, movie.posterPath)
        }
    }

    override fun selectAllMovies(): Flow<List<MovieEntity>> {   //todo apply caching strategy
        return dbQuery.selectAllMovies().asFlow().mapToList()
    }

    override suspend fun getMovieById(movieId: Long): MovieEntity? {
        return withContext(Dispatchers.Default) {
            dbQuery.selectMovieById(movieId).executeAsOneOrNull()
        }
    }

    override suspend fun deleteAllMovies() {
        withContext(Dispatchers.Default) {
            dbQuery.deleteAllMovies()
        }
    }
}


