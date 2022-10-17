package com.example.kast.android.ui.home.movie_details

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kast.presentation.viewModel.MovieDetailsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(movieId: Long, navController: NavController) {
    val viewModel = getViewModel<MovieDetailsViewModel>(parameters = { parametersOf(movieId) })
    val state by viewModel.state.collectAsState()

    Box {
        state.movie?.let { movie ->
            Text(text = movie.title)
        }
    }
}

