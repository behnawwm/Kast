package com.example.kast.data.source.remote.movie_category

import com.example.kast.data.source.remote.model.TmdbCategory
import com.example.kast.data.source.remote.model.TmdbMovie
import com.example.kast.data.source.remote.model.TmdbPagingResult

interface MovieCategoryService {
    suspend fun getMovieCategories(url: String): TmdbPagingResult<TmdbCategory>?
}