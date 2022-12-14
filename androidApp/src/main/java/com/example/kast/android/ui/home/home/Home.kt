package com.example.kast.android.ui.home.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.presentation.viewModel.MovieViewModel
import com.example.kast.android.theme.*
import com.example.kast.android.ui.shared_components.MovieListWithHeader
import com.example.kast.domain.model.CategoryType
import com.example.kast.domain.model.CategoryView
import com.example.kast.domain.model.MovieView
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (movie: MovieView) -> Unit,
    onMovieLongClick: (movie: MovieView) -> Unit,
    onOptionsClick: (movie: MovieView) -> Unit,
    viewModel: MovieViewModel = getViewModel(),
) {
    CollapsingToolbarScaffold(
        state = rememberCollapsingToolbarScaffoldState(),
        toolbar = {
            HomeTopBar()
        },
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        modifier = Modifier.background(background),
    ) {

        val state by remember { viewModel.state }
        MovieCategoriesList(
            viewModel = viewModel,
            categories = state.categories,
            onMovieClick = onMovieClick,
            onMovieLongClick = onMovieLongClick,
            onOptionsClick = onOptionsClick,
            modifier = Modifier
                .fillMaxSize()
//                .padding(paddingValues)
        )
    }
}

@Composable
fun MovieCategoriesList(
    viewModel: MovieViewModel,
    categories: List<CategoryView>,
    onMovieClick: (MovieView) -> Unit,
    onMovieLongClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (categories.isEmpty()) {
            CircularProgressIndicator(color = orange, modifier = Modifier.align(Alignment.Center))
        } else LazyColumn() {
            items(categories) { category ->
                MovieListWithHeader(
                    categoryView = category,
                    onMovieClick = onMovieClick,
                    onMovieLongClick = onMovieLongClick,
                    onOptionsClick = onOptionsClick,
                    modifier = Modifier.fillMaxSize(),
                    onMoreClick = {},
                    onRetry = {
//                        viewModel.getMoviesByType(category.type)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onMovieClick = {}, onOptionsClick = {}, onMovieLongClick = {})
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCategoriesListPreview() {
    MovieCategoriesList(
        getViewModel(),
        listOf(
            CategoryView(CategoryType.NowPlaying, "sdsa", emptyList()),
            CategoryView(CategoryType.NowPlaying, "sdsa", emptyList()),
            CategoryView(CategoryType.NowPlaying, "sdsa", emptyList()),
        ),
        onMovieClick = {},
        onMovieLongClick = {},
        onOptionsClick = {},
        modifier = Modifier.fillMaxSize()
    )
}