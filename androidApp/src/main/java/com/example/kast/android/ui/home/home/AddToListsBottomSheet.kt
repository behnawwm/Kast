package com.example.kast.android.ui.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.AddToListsViewModel
import com.example.kast.MovieViewModel
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.example.kast.android.theme.bottomNavigationSelectedIconBackground
import com.example.kast.android.theme.orange
import com.example.kast.data.model.MovieView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.getViewModel

@Composable
fun AddToListsBottomSheet(movie: MovieView, onBookmarkClick: () -> Unit) {
    val viewModel = getViewModel<AddToListsViewModel>()

    viewModel.setMovie(movie)
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .background(bottomNavigationContainerColor)
            .padding(20.dp)
    ) {
        AddToListsItem(Icons.Default.Circle, "Mark as watched") {
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddToListsItem(Icons.Default.Bookmark, "Add to watchlist") {
            viewModel.addMovieToWatchlist()
            onBookmarkClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        AddToListsItem(Icons.Default.Book, "Add to Collection") {

        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AddToListsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .clip(RoundedCornerShape(16.dp))
            .background(bottomNavigationSelectedIconBackground)
            .padding(16.dp)
    ) {
        Icon(icon, contentDescription = "", tint = Color.White)
        Text(text = title, modifier = Modifier.padding(start = 16.dp), color = Color.White)
    }
}

@Preview
@Composable
fun AddToListsBottomSheetPreview() {
    AddToListsBottomSheet(MovieView(1, "test", 1.3, ""), {})
}