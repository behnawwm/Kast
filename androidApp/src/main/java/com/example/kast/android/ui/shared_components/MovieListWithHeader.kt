package com.example.kast.android.ui.shared_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.orange
import com.example.kast.android.theme.subtitleColor
import com.example.kast.android.theme.titleColor
import com.example.kast.data.model.CategoryType
import com.example.kast.data.model.CategoryView
import com.example.kast.data.model.MovieView


@Composable
fun MovieListWithHeader(
    categoryView: CategoryView,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.height(200.dp)) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = categoryView.type.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            )
            Text(
                text = categoryView.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = subtitleColor,
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            )
        }
        MovieList(
            movies = categoryView.movies,
            onMovieClick = onMovieClick,
            onOptionsClick = onOptionsClick,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MovieList(
    movies: List<MovieView>,
    onMovieClick: (MovieView) -> Unit,
    onOptionsClick: (MovieView) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty())
        Box(modifier = modifier) {
            CircularProgressIndicator(
                color = orange,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
        }
    else
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

@Preview
@Composable
fun MovieListWithHeaderPreview() {
    MovieListWithHeader(
        categoryView = CategoryView(CategoryType.NowPlaying, "Movies", emptyList()),
        onMovieClick = {},
        onOptionsClick = {})
}

@Preview
@Composable
fun MovieListWithHeaderPreview2() {
    MovieListWithHeader(
        categoryView = CategoryView(
            CategoryType.NowPlaying, "Movies", listOf(
                MovieView(1, "Thor", 1.4, "")
            )
        ),
        onMovieClick = {},
        onOptionsClick = {})
}