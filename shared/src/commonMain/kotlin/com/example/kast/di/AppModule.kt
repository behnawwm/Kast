package com.example.kast.di

import com.example.kast.data.repository.FakeRepository
import com.example.kast.data.repository.MovieRepository
import com.example.kast.data.source.local.MoviesDatabase
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.MovieService
import com.example.kast.data.source.remote.MovieServiceImpl
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

    single<ApiClient>{
        ApiClient(
            get()
        )
    }

    single {
        MovieRepository(
            get(),
            get()
        )
    }

    single {
        FakeRepository()
    }
}

expect val platformModule: Module