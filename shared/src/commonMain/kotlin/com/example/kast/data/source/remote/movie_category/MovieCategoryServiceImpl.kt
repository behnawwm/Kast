package com.example.kast.data.source.remote.movie_category

import com.example.kast.data.model.TmdbCategory
import com.example.kast.data.model.TmdbPagingResult
import com.example.kast.data.source.remote.ApiClient

class MovieCategoryServiceImpl(
    private val apiClient: ApiClient
) : MovieCategoryService {

    override suspend fun getMovieCategories(url: String): TmdbPagingResult<TmdbCategory>? {
        TODO("Not yet implemented")
    }
}