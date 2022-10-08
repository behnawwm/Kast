package com.example.kast.data.repository

import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.MovieView
import com.example.kast.data.source.local.movie.MoviesDatabase
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.mapper.toMovieEntity
import com.example.kast.domain.mapper.toMovieView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val apiServices: MovieService,
    private val database: MoviesDatabase,
) : MovieRepository {

    override fun getDynamicMovies(categoryType: CategoryType): Flow<List<MovieView>?> {
        return flow {
            val result = apiServices.getMovies(categoryType.url)
            emit(result?.results?.map { it.toMovieView() })
        }.flowOn(Dispatchers.Default)
    }

    @OptIn(FlowPreview::class)
    override fun selectAllMovies(): Flow<List<MovieView>> {
        return database.selectAllMovies().flatMapConcat {
            flow {
                emit(
                    it.map {
                        it.toMovieView()
                    }
                )
            }
        }
    }

    override suspend fun selectMovieById(movieId: Long): MovieView? {
        return database.getMovieById(movieId)?.toMovieView()
    }

    override suspend fun insertMovie(movie: MovieView) {
        database.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteAllMovies() {
        database.deleteAllMovies()
    }
}



