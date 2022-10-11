package com.example.kast.utils

import arrow.core.Either
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class FlowUseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Flow<Either<Failure, Type>>

    operator fun invoke(
        params: Params,
        onResult: (Flow<Either<Failure, Type>>) -> Unit = {},
    ) {
        onResult(run(params))
    }

}