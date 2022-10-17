package com.example.kast.android.ui.watchlist

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kast.presentation.viewModel.WatchlistViewModel
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.example.kast.android.ui.shared_components.MovieCard
import com.example.kast.domain.model.MovieView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WatchlistScreen() {     //todo recompositions and fix performance
    val viewModel = getViewModel<WatchlistViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            WatchListTopBar(
                isExpanded = state.isExpanded,
                onToggleExpandClick = { isExpanded ->
                    viewModel.onToggleExpandClick(isExpanded)
                }
            )
        }
    ) { paddingValues ->

        val pagerState = rememberPagerState()

        val watchListTabPosition = 0
        val historyTabPosition = 1

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TopHeaderTitles(pagerState, watchListTabPosition, historyTabPosition)
            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier
            ) { currentPage ->

                when (currentPage) {
                    watchListTabPosition -> WatchlistTab(state = state)
                    historyTabPosition -> HistoryTab(state = state)
                }
            }
        }


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopHeaderTitles(pagerState: PagerState, watchListTabPosition: Int, historyTabPosition: Int) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .alpha(
                    when (pagerState.currentPage) {
                        watchListTabPosition -> 1f
                        historyTabPosition -> 0.5f
                        else -> 0f
                    }
                )
                .clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(watchListTabPosition)
                    }
                },
            text = "Watchlist",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .alpha(
                    when (pagerState.currentPage) {
                        watchListTabPosition -> 0.5f
                        historyTabPosition -> 1f
                        else -> 0f
                    }
                )
                .clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(historyTabPosition)
                    }
                },
            text = "History",
            style = MaterialTheme.typography.headlineMedium
        )
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HistoryTab(state: WatchlistViewModel.State) {
    val historyPagerState = rememberPagerState()
    val historyTabIndex = historyPagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabLayout(
            tabData = state.historyTabData,
            tabIndex = historyTabIndex,
            pagerState = historyPagerState,
            coroutineScope = coroutineScope
        )

        HorizontalPager(
            count = state.historyTabData.size,
            state = historyPagerState,
            modifier = Modifier
        ) { currentPage ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Not Implemented",
                    style = MaterialTheme.typography.bodySmall
                )

            }

        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun WatchlistTab(state: WatchlistViewModel.State) {
    val watchlistTabData = state.watchlistTabData
    val watchlistPagerState = rememberPagerState()
    val watchlistTabIndex = watchlistPagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabLayout(
            tabData = watchlistTabData,
            tabIndex = watchlistTabIndex,
            pagerState = watchlistPagerState,
            coroutineScope = coroutineScope
        )

        HorizontalPager(
            count = state.watchlistTabData.size,
            state = watchlistPagerState,
            modifier = Modifier
        ) { currentPage ->
            when (currentPage) {
                0 -> {
                    LazyGridWithError(state.allMovies.filter { it.isBookmarked },
                        state.isExpanded,
                        state.databaseError)
                }
                1 -> {
                    LazyGridWithError(state.allMovies.filter { it.isWatched },
                        state.isExpanded,
                        state.databaseError)
                }
                2 -> {
                    LazyGridWithError(state.allMovies.filter { it.isCollected },
                        state.isExpanded,
                        state.databaseError)
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridWithError(movies: List<MovieView>, isExpanded: Boolean, databaseError: String?) {
    if (databaseError == null)
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isExpanded) 1 else 3),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                movies,
                key = { it.id }
            ) {
                if (isExpanded)
                    MovieExtendedCard(
                        movie = it,
                        onMovieClick = {},
                        onOptionsClick = {},
                        modifier = Modifier
                            .padding(8.dp)
                            .animateItemPlacement()
                    )
                else
                    MovieCard(
                        movie = it,
                        onOptionsClick = {},
                        modifier = Modifier
                            .padding(8.dp)
                            .animateItemPlacement()
                    )
                Divider(color = bottomNavigationContainerColor)
            }
        }
    else
        Text(text = databaseError,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
        )
}


