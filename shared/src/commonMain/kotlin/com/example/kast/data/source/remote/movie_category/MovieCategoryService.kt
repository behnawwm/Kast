package com.example.kast.data.source.remote.movie_category

import com.example.kast.data.model.TmdbCategory
import com.example.kast.data.model.TmdbMovie
import com.example.kast.data.model.TmdbPagingResult

interface MovieCategoryService {
    suspend fun getMovieCategories(url: String): TmdbPagingResult<TmdbCategory>?
}