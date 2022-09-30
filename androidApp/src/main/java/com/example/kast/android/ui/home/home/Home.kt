package com.example.kast.android.ui.home.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.FakeData
import com.example.kast.MovieViewModel
import com.example.kast.android.theme.*
import com.example.kast.android.ui.shared_components.MovieCard
import com.example.kast.data.model.Category
import com.example.kast.data.model.MovieView
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (movie: MovieView) -> Unit,
    onOptionsClick: (movie: MovieView) -> Unit,
    viewModel: MovieViewModel = getViewModel()
) {
    Scaffold(
        topBar = {
            HomeTopBar()
        }
    ) { paddingValues ->
        val state by remember { viewModel.state }
        MovieCategoriesList(
            categories = state.categories,
            onMovieClick = onMovieClick,
            onOptionsClick = onOptionsClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun MovieCategoriesList(
    categories: List<Category>,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (categories.isEmpty()) {
            CircularProgressIndicator(color = orange, modifier = Modifier.align(Alignment.Center))
        } else LazyColumn() {
            items(categories) { category ->
                MovieListWithHeader(
                    category,
                    onMovieClick = onMovieClick,
                    onOptionsClick = onOptionsClick
                )
            }
        }

    }
}

@Composable
fun MovieListWithHeader(
    category: Category,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
    ) {
        Text(
            text = category.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = titleColor,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(
            text = category.subtitle,
            style = MaterialTheme.typography.labelSmall,
            color = subtitleColor,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
    }
    MovieList(
        movies = category.movies,
        onMovieClick = onMovieClick,
        onOptionsClick = onOptionsClick,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieList(
    movies: List<MovieView>,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                onMovieClick = onMovieClick,
                onOptionsClick = onOptionsClick
            )
        }
    }
}




@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onMovieClick = {}, onOptionsClick = {})
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCategoriesListPreview() {
    MovieCategoriesList(
        FakeData.categories,
        onMovieClick = {},
        onOptionsClick = {},
        modifier = Modifier.fillMaxSize()
    )
}