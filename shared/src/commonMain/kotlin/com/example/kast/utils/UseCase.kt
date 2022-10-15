package com.example.kast.utils

import arrow.core.Either
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {  //todo add failure sealed classes support

    abstract suspend fun run(params: Params): Either<Failure, Type>

    @OptIn(DelicateCoroutinesApi::class)
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.Default) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

}