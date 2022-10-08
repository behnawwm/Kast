package com.example.kast.android.ui.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.blueBookmark
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.example.kast.android.theme.darkGreenCollection
import com.example.kast.android.theme.greenWatchlist
import com.example.kast.domain.model.MovieView


@Composable
fun TagChips(movie: MovieView, modifier: Modifier = Modifier) {
    val tags = mutableListOf<TagChipDataView>()
    if (movie.isBookmarked)
        tags.add(TagChipDataView(Icons.Default.Bookmark, blueBookmark))
    if (movie.isWatched)
        tags.add(TagChipDataView(Icons.Default.Check, greenWatchlist))
    if (movie.isCollected)
        tags.add(TagChipDataView(Icons.Default.Book, darkGreenCollection))

    TagChips(tags = tags, modifier = modifier)
}

@Composable
fun TagChips(
    tags: List<TagChipDataView>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        tags.forEachIndexed { pos, item ->
            Box(
                modifier = Modifier
                    .offset((-6 * pos).dp)
                    .border(2.dp, bottomNavigationContainerColor, CircleShape)
                    .clip(CircleShape)
                    .background(item.backgroundColor)
            ) {
                Icon(
                    item.icon,
                    "",
                    tint = item.iconTint,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}