package com.example.kast.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.kast.presentation.AddToListsViewModel
import com.example.kast.presentation.MovieViewModel
import com.example.kast.presentation.WatchlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule(app: Application) = module {

    single<Context> {
        app
    }

    viewModel {
        MovieViewModel(get(), get())
    }
    viewModel {
        AddToListsViewModel(get())
    }
    viewModel {
        WatchlistViewModel(get())
    }

    single<SharedPreferences> {
        get<Context>().getSharedPreferences("KAST_SETTING", Context.MODE_PRIVATE)
    }
}