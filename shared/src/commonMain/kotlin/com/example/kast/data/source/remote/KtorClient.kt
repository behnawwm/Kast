package com.example.kast.data.source.remote

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*


class ApiClient(
    private val engine: HttpClientEngine,
    val baseUrl: String = BASE_URL_TMDB
) {

    val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
            Logging {
                logger = Logger.DEFAULT
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO
        }
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value
                println("HTTP status: $statusCode")
            }

            handleResponseExceptionWithRequest { cause, request ->
                throw cause
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 60_000
            socketTimeoutMillis = 60_000
        }

    }


    suspend inline fun <reified T : Any> getResponse(endpoint: String): T? {
        val url = baseUrl + endpoint
        try {
            // please notice, Ktor Client is switching to a background thread under the hood
            // so the http call doesn't happen on the main thread, even if the coroutine has been launched on Dispatchers.Main
            return client.get {
                url(url)
                parameter("api_key","29227321b612ab6cd44435b4403a2f63")
            }.body<T>()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
