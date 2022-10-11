package com.example.kast.di

import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.data.source.local.movie.MoviesDao
import com.example.kast.data.source.local.movie.MoviesDatabase
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.data.source.remote.movie.MovieServiceImpl
import com.example.kast.data.source.remote.movie_category.MovieCategoryService
import com.example.kast.data.source.remote.movie_category.MovieCategoryServiceImpl
import com.example.kast.domain.repository.MovieCategoryRepository
import com.example.kast.domain.repository.MovieRepository
import com.example.kast.domain.usecase.GetLocalMoviesUseCase
import com.example.kast.domain.usecase.GetRemoteMovieCategoriesUseCase
import com.example.kast.domain.usecase.GetRemoteMoviesByTypeUseCase
import com.example.kast.domain.usecase.InsertMovieUseCase
import com.example.kast.domain.usecase.home.GetMovieCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    //todo
//    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
//    val koin = koinApplication.koin
//    // doOnStartup is a lambda which is implemented in Swift on iOS side
//    val doOnStartup = koin.get<() -> Unit>()
//    doOnStartup.invoke()

    return koinApplication
}

private val coreModule = module {

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

    single<ApiClient> {
        ApiClient(
            get()
        )
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get(), get())
    }

    single<MovieCategoryRepository> {
        FakeMovieCategoryRepositoryImpl()
    }

    //todo different module for use cases
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
        GetRemoteMoviesByTypeUseCase(get())
    }
    single {
        GetRemoteMovieCategoriesUseCase(get())
    }

}

expect val platformModule: Module