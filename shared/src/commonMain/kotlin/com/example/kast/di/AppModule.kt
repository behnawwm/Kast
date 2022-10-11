package com.example.kast.di

import com.example.kast.data.source.remote.ApiClient
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule + platformModule + coreModule + dataSourceModule + repositoryModule + useCaseModule
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
        ApiClient(
            get()
        )
    }

}

expect val platformModule: Module