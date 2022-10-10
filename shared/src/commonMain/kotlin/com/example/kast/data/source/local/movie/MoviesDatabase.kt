package com.example.kast.data.source.local.movie

import com.example.kast.KastDb
import com.example.kast.MovieEntity
import com.example.kast.data.source.local.DatabaseDriverFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class MoviesDatabase(
    databaseDriverFactory: DatabaseDriverFactory,
    private val backgroundDispatcher: CoroutineDispatcher,
) : MoviesDao {
    private val database = KastDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.kastDbQueries

    override suspend fun insertMovie(movie: MovieEntity) {
        withContext(backgroundDispatcher) {
            dbQuery.insertMovie(
                movie.id,
                movie.title,
                movie.rating,
                movie.posterPath,
                movie.isBookmarked,
                movie.bookmarkDateTime,
                movie.isWatched,
                movie.watchDateTime,
                movie.isCollected,
                movie.collectDateTime
            )
        }
    }

    override suspend fun selectAllMovies(): List<MovieEntity> {   //todo apply caching strategy
        return dbQuery
            .selectAllMovies()
            .executeAsList()
    }

    override suspend fun getMovieById(movieId: Long): MovieEntity? {
        return withContext(backgroundDispatcher) {
            dbQuery.selectMovieById(movieId).executeAsOneOrNull()
        }
    }

    override suspend fun deleteAllMovies() {
        withContext(backgroundDispatcher) {
            dbQuery.deleteAllMovies()
        }
    }
}


