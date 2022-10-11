package com.example.kast.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.kast.presentation.viewModel.AddToListsViewModel
import com.example.kast.presentation.viewModel.MovieViewModel
import com.example.kast.presentation.viewModel.WatchlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule(app: Application) = module {

    single<Context> {
        app
    }

    viewModel {
        MovieViewModel(get())
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