package com.example.kast.di

import com.example.kast.data.repository.MovieRepository
import com.example.kast.data.source.local.MoviesDatabase
import com.example.kast.data.source.remote.ApiClient
import com.example.kast.data.source.remote.MovieServices
import com.example.kast.data.source.remote.MovieServicesImpl
import com.example.kast.data.source.remote.TmdbWebConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
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
    single<MovieServices> {
        MovieServicesImpl(
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
}

expect val platformModule: Module