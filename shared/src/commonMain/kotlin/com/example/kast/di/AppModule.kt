package com.example.kast.di


import com.example.kast.data.source.local.Database
import com.example.kast.data.source.local.DatabaseDriverFactory
import com.example.kast.data.source.remote.TmdbWebConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    enableNetworkLogs: Boolean = true,
    baseUrl: String = TmdbWebConfig.BASE_URL_TMDB,
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(appModule())
    }

// called by iOS etc
fun initKoin(baseUrl: String = TmdbWebConfig.BASE_URL_TMDB) =
    initKoin(enableNetworkLogs = true, baseUrl) {

    }

fun appModule() = module {

    single { createJson() }

    single {
        createHttpClient(
            get(),
            get(),
            enableNetworkLogs = true
        )
    }
    single {
        Database(get())
    }

}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean
) =
    HttpClient(httpClientEngine) {

        install(ContentNegotiation) {
            json(json)
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

