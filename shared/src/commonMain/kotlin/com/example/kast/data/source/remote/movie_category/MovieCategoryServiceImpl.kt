package com.example.kast.data.source.remote.movie_category

import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.data.source.remote.model.TmdbPagingResult
import com.example.kast.data.source.remote.ApiClient

class MovieCategoryServiceImpl(
    private val apiClient: ApiClient
) : MovieCategoryService {

    override suspend fun getMovieCategories(url: String): TmdbPagingResult<TmdbCategory>? {
        TODO("Not yet implemented")
    }
}