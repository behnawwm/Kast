package com.example.kast.di

import com.example.kast.data.source.local.movie.MoviesDao
import com.example.kast.data.source.local.movie.MoviesDatabase
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.data.source.remote.movie.MovieServiceImpl
import com.example.kast.data.source.remote.movie_category.MovieCategoryService
import com.example.kast.data.source.remote.movie_category.MovieCategoryServiceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val dataSourceModule = module {
    single<MoviesDao> {
        MoviesDatabase(
            get(),
            Dispatchers.Default
        )
    }

    single<MovieService> {
        MovieServiceImpl(
            get()
        )
    }

    single<MovieCategoryService> {
        MovieCategoryServiceImpl(
            get()
        )
    }
}