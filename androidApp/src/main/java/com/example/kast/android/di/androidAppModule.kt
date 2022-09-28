package com.example.kast.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.kast.KastApp
import com.example.kast.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module

fun appModule(app: Application) = module {

    single<Context> {
        app
    }

    viewModel {
        MovieViewModel(get())
    }

    single<SharedPreferences> {
        get<Context>().getSharedPreferences("KAST_SETTING", Context.MODE_PRIVATE)
    }
}