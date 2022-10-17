package com.example.kast.di

import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetMoviesWithStatusByTypeUseCase
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.domain.usecase.GetMovieCategoriesUseCase
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
}