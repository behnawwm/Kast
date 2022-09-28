package com.example.kast

import android.app.Application
import android.content.Context
import com.example.kast.android.di.appModule
import com.example.kast.di.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


@HiltAndroidApp
class KastApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            appModule(this@KastApp)
        )
    }

}