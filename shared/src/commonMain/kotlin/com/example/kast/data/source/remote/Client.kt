package com.example.kast.data.source.remote

import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.math.log


class ApiClient  {

    val baseUrl = BASE_URL_TMDB

    val client = HttpClient {
        install(ContentNegotiation) {
            json()
            Logging {
                logger = Logger.DEFAULT
            }
        }
        install(ResponseObserver){
            onResponse { response->
                println("HTTP status: ${response.status.value}")
            }
        }
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value

                println("HTTP status: $statusCode")


            }

            handleResponseException { cause: Throwable ->
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
            return client.get{
                url("http://192.168.0.60:805/lego.web/api/KasraFood/movies/movies")
//                url(url)
//                parameter("api_key","29227321b612ab6cd44435b4403a2f63")
            }.body<T>()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getHtml(): String {
        val response = client.get("https://ktor.io/docs")
        return response.bodyAsText()
    }


}
