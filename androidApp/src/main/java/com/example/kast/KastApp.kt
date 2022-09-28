package com.example.kast

import android.app.Application
import com.example.kast.di.initKoin
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@HiltAndroidApp
class KastApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            enableNetworkLogs = BuildConfig.DEBUG
        ) {
            androidContext(this@KastApp)
//            modules()
        }
    }

}