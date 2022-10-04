package com.example.kast.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kast.data.model.CategoryTypeUrl
import com.example.kast.data.model.MovieView
import com.example.kast.data.repository.MovieRepository

class MoviesSource(
    private val movieRepository: MovieRepository,
) : PagingSource<Int, MovieView>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieView> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse =
                movieRepository.getDynamicMovies(CategoryTypeUrl.UpcomingUrl, nextPage)

            LoadResult.Page(
                data = movieListResponse?.results.orEmpty().map { it.toMovieView() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = movieListResponse?.page?.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieView>): Int? {
        return null
    }
}