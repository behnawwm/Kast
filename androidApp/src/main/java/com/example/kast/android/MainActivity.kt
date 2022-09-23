package com.example.kast.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.kast.android.theme.KastTheme
import com.example.kast.android.utils.SetSystemBarColors
import com.example.kast.android.utils.addEmptyLines
import com.example.kast.android.utils.getImageLoader

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by remember {
                viewModel.state
            }
            KastTheme {
                SetSystemBarColors()

                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                Scaffold(
                    backgroundColor = Color.DarkGray,
                    bottomBar = {

                    },
                    topBar = {

                    },
                    scaffoldState = scaffoldState,
                    snackbarHost = {

                    }
                ) {
                    it
                    MovieCategoriesScreen(state.categories, scaffoldState)
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    KastTheme {
        val scaffoldState = rememberScaffoldState()

        MovieCategoriesScreen(FakeData.categories, scaffoldState)
    }
}

@Composable
fun MovieCategoriesScreen(categories: List<Category>, scaffoldState: ScaffoldState) {
    if (categories.isEmpty()) {
        CircularProgressIndicator()
    }
    else
        LazyColumn() {
            items(categories) { category ->
                MovieListWithHeader(category, scaffoldState)
            }
        }
}

@Composable
fun MovieListWithHeader(category: Category, scaffoldState: ScaffoldState) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
    ) {
        Text(
            text = category.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
        Text(
            text = category.subtitle,
            style = MaterialTheme.typography.subtitle2,
            color = Color.LightGray,
            modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
        )
    }
    MovieList(
        movies = category.movies,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
    )
}

@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable {

            }
    ) {

        Card(shape = RoundedCornerShape(8.dp)) {
            Box(contentAlignment = Alignment.TopEnd) {
                SubcomposeAsyncImage(
                    imageLoader = LocalContext.current.getImageLoader(),
                    model = movie.imageUrl,
                    loading = {
                        CircularProgressIndicator()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentDescription = movie.title
                )
                Text(
                    text = movie.rating.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(4.dp, 2.dp, 4.dp, 2.dp)
                        .alpha(0.7f)
                        .background(
                            Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp, 2.dp, 4.dp, 2.dp),
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title.addEmptyLines(1),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Light
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.MoreVert,
                stringResource(id = R.string.more),
                tint = Color.White,
                modifier = Modifier
                    .clickable {

                    }
            )
        }

    }

}
