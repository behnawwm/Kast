package com.example.kast.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.android.theme.KastTheme

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by remember {
                viewModel.state
            }
            KastTheme {
                MovieCategoriesScreen(state.categories)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    KastTheme {
        MovieCategoriesScreen(FakeData.categories)
    }
}

@Composable
fun MovieCategoriesScreen(categories: List<Category>) {
    if (categories.isEmpty())
        CircularProgressIndicator()
    else
        LazyColumn() {
            items(categories) { category ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(16.dp)
                    )
                    MovieList(movies = category.movies)
                }
            }
        }
}

@Composable
fun MovieList(movies: List<Movie>) {
    LazyRow() {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Card(modifier = Modifier.width(100.dp)) {
        Text(text = movie.title)
    }
}