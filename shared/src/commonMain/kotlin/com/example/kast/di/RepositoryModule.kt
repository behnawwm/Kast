package com.example.kast.di

import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get(), get())
    }

    single<MovieCategoryRepository> {
        FakeMovieCategoryRepositoryImpl()
    }
}