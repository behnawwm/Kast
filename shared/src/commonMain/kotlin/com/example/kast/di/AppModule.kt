package com.example.kast.di

import com.example.kast.data.repository.FakeMovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieCategoryRepositoryImpl
import com.example.kast.data.repository.MovieRepositoryImpl
import com.example.kast.data.source.local.MoviesDatabase
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.movie.MovieService
import com.example.kast.data.source.remote.movie.MovieServiceImpl
import com.example.kast.data.source.remote.movie_category.MovieCategoryService
import com.example.kast.data.source.remote.movie_category.MovieCategoryServiceImpl
import com.example.kast.domain.repository.MovieCategoryRepository
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

    single {
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

    single {
        MovieRepositoryImpl(
            get(),
            get()
        )
    }

    single<MovieCategoryRepository> {
//        MovieCategoryRepositoryImpl()
        FakeMovieCategoryRepositoryImpl()
    }
}

expect val platformModule: Module