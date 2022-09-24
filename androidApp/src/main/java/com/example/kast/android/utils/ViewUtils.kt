package com.example.kast.android.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.example.kast.android.theme.KastTheme
import com.example.kast.android.theme.background
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetDarkSystemBarColors(statusBarColor: Color, navigationBarColor: Color) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(navigationBarColor)

        onDispose {}
    }
}