package com.example.kast.data.source.local

import com.example.kast.KastDb
import com.example.kast.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class MoviesDatabase(
    databaseDriverFactory: DatabaseDriverFactory,
    private val backgroundDispatcher: CoroutineDispatcher
) : MoviesDao {
    private val database = KastDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.kastDbQueries

    override suspend fun insertMovie(movie: MovieEntity) {
        withContext(backgroundDispatcher) {
            dbQuery.insertMovie(movie.id, movie.title, movie.rating, movie.posterPath)
        }
    }

    override fun selectAllMovies(): Flow<List<MovieEntity>> {   //todo apply caching strategy
        return dbQuery
            .selectAllMovies()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)
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


