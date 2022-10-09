package com.example.kast.utils

import io.ktor.client.plugins.*


sealed interface Failure {

    sealed class NetworkFailure(val exception: ResponseException) : Failure {
        class RedirectException(exception: RedirectResponseException) :
            NetworkFailure(exception)

        class ClientException(exception: ClientRequestException) :
            NetworkFailure(exception)

        class ServerException(exception: ServerResponseException) :
            NetworkFailure(exception)
    }

    sealed interface DatabaseFailure : Failure {
        sealed interface FindFailure : DatabaseFailure {
            object ItemNotFoundInDb : FindFailure
        }
        sealed interface ReadFailure : DatabaseFailure {
            object EmptyList : ReadFailure
        }
    }

}