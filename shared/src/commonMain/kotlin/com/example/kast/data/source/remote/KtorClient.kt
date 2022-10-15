package com.example.kast.data.source.remote

import arrow.core.Either
import com.example.kast.data.source.remote.TmdbWebConfig.BASE_URL_TMDB
import com.example.kast.data.source.remote.TmdbWebConfig.TMDB_API_KEY
import com.example.kast.utils.Failure
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ApiClient(
    private val engine: HttpClientEngine,
    val baseUrl: String = BASE_URL_TMDB,
) {

    val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
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
            level = LogLevel.ALL
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
        //todo timeout
//        install(HttpTimeout) {
//            requestTimeoutMillis = 60_000
//            connectTimeoutMillis = 60_000
//            socketTimeoutMillis = 60_000
//        }

    }


    suspend inline fun <reified T : Any> getResponseWithApiKey(endpoint: String): Either<Failure.NetworkFailure, T> {
        val url = baseUrl + endpoint
        return try {
            // please notice, Ktor Client is switching to a background thread under the hood
            // so the http call doesn't happen on the main thread, even if the coroutine has been launched on Dispatchers.Main
            Either.Right(
                client.get {
                    url(url)
                    parameter("api_key", TMDB_API_KEY)
                }.body()
            )
        } catch (e: RedirectResponseException) { // for 3xx
            Either.Left(Failure.NetworkFailure.RedirectException(e))
        } catch (e: ClientRequestException) { // for 4xx
            Either.Left(Failure.NetworkFailure.ClientException(e))
        } catch (e: ServerResponseException) { // for 5xx
            Either.Left(Failure.NetworkFailure.ServerException(e))
        } catch (e: Exception) {
            Either.Left(Failure.NetworkFailure.UnknownException(e))
        }
    }

}
