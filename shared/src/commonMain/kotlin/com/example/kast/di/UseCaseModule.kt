package com.example.kast.di

import com.example.kast.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single {
        InsertMovieUseCase(get())
    }
    single {
        GetMovieCategoriesUseCase(get(), get())
    }
    single {
        GetLocalMoviesUseCase(get())
    }
    single {
        GetMoviesWithStatusByTypeUseCase(get())
    }
    single {
        GetRemoteMovieCategoriesUseCase(get())
    }
    single {
        GetMovieDetailsUseCase(get())
    }
}