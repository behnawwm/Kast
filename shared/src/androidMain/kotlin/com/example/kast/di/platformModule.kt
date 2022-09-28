package com.example.kast.di

import com.example.kast.MovieViewModel
import com.example.kast.data.source.local.DatabaseDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<DatabaseDriverFactory> {
        DatabaseDriverFactory(get())
    }

    single<HttpClientEngine> {
        Android.create {
            //configs
        }
    }

}