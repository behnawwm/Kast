package com.example.kast.android.ui.home.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.kast.android.theme.background
import com.example.kast.android.theme.orange
import com.example.kast.android.ui.shared_components.ExpandingText
import com.example.kast.presentation.viewModel.MovieDetailsViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieDetailScreen(movieId: Long, navController: NavController) {
    val viewModel = getViewModel<MovieDetailsViewModel>(parameters = { parametersOf(movieId) })
    val state by viewModel.state.collectAsStateWithLifecycle()

    state.movie?.let { movie ->
        CollapsingToolbarScaffold(
            state = rememberCollapsingToolbarScaffoldState(),
            toolbar = {

            },
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            modifier = Modifier.fillMaxSize().background(background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(), enabled = true)
            ) {

                SubcomposeAsyncImage(
                    model = movie.imageUrl,
                    loading = {
                        CircularProgressIndicator(
                            color = orange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    },
                    error = {
                        Image(Icons.Default.BrokenImage, contentDescription = "")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentDescription = movie.title,
                )
                Text(
                    text = movie.title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )

                ExpandingText(
                    text = movie.overview.orEmpty(),
                    labelTextColor = orange,
                    isShowLessEnabled = false,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = movie.genres.orEmpty().map { it.name }.joinToString(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}

