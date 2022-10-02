package com.example.kast.android.ui.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kast.AddToListsViewModel
import com.example.kast.android.theme.*
import com.example.kast.data.model.MovieView
import org.koin.androidx.compose.getViewModel

@Composable
fun AddToListsBottomSheet(movie: MovieView, navController: NavController) {
    val viewModel = getViewModel<AddToListsViewModel>()

    viewModel.setMovie(movie)
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(bottkomNavigationContainerColor)
            .padding(top = 0.dp, end = 0.dp)
    ) {
        Surface(
            modifier = Modifier
                .padding(top = 6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .width(48.dp)
                .height(4.dp)
                .align(Alignment.TopCenter)
        ) {}
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 0.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(16.dp))

            AddToListsItem(
                isActive = movie.isWatched,
                inactiveIcon = Icons.Default.Circle,
                activeIcon = Icons.Default.Check,
                activeTint = greenWatchlist,
                actionDate = movie.watchDate,
                inactiveTitle = "Mark as watched",
                activeTitle = "Watched"
            ) {
                //todo
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddToListsItem(
                isActive = movie.isBookmarked,
                inactiveIcon = Icons.Outlined.Bookmark,
                activeIcon = Icons.Default.Bookmark,
                activeTint = blueBookmark,
                actionDate = movie.bookmarkDate,
                inactiveTitle = "Add to watchlist",
                activeTitle = "Watched"
            ) {
                viewModel.addMovieToWatchlist()
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddToListsItem(
                isActive = movie.isCollected,
                inactiveIcon = Icons.Outlined.Book,
                activeIcon = Icons.Default.Book,
                activeTint = darkGreenCollection,
                actionDate = movie.collectDate,
                inactiveTitle = "Add to Collection",
                activeTitle = "Collected"
            ) {
                //todo
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun AddToListsItem(
    isActive: Boolean,
    inactiveIcon: ImageVector,
    activeIcon: ImageVector,
    activeTint: Color,
    actionDate: String?,
    inactiveTitle: String,
    activeTitle: String,
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
            .background(if (isActive) activeTint else bottomNavigationSelectedIconBackground)
            .padding(start = 16.dp, 16.dp, 16.dp, 16.dp)
    ) {
        Icon(
            if (isActive) activeIcon else inactiveIcon,
            contentDescription = "",
            tint = Color.White
        )
        Column {
            Text(
                text = if (isActive) activeTitle else inactiveTitle,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            )
            if (isActive)
                actionDate?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 16.dp),
                        color = Color.White.copy(0.9f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
        }
    }
}


@Preview
@Composable
fun AddToListsBottomSheetPreview() {
    AddToListsBottomSheet(MovieView(1, "test", 1.3, ""), NavController(LocalContext.current))
}