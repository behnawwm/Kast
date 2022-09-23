package com.example.kast.android.utils

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetSystemBarColors(){
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(Color.Black)
        systemUiController.setNavigationBarColor(Color.Black)
        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {}
    }
}