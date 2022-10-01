package com.example.kast.android.ui.watchlist

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kast.FakeData.sampleMovieList
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WatchlistScreen() {
    Scaffold(topBar = { WatchListTopBar() }) { paddingValues ->

        fun watchListTabItems() = listOf(
            HorizontalPagerContent(title = "Movie"),
            HorizontalPagerContent(title = "TvShow"),
            HorizontalPagerContent(title = "Season"),
            HorizontalPagerContent(title = "Episodes"),
        )

        fun historyListTabItems() = listOf(
            HorizontalPagerContent(title = "Movie"),
            HorizontalPagerContent(title = "TvShow"),
            HorizontalPagerContent(title = "Season"),
        )


        val watchListTabItems = watchListTabItems()
        val historyListTabItems = historyListTabItems()
        val pagerState = rememberPagerState()

        val watchlistPagerState = rememberPagerState()
        val watchlistTabIndex = watchlistPagerState.currentPage

        val historyPagerState = rememberPagerState()
        val historyTabIndex = historyPagerState.currentPage

        val coroutineScope = rememberCoroutineScope()
        val watchlistTabData = listOf(
            "Movies", "TV Shows", "Seasons", "Episodes"
        )
        val historyTabData = listOf(
            "Movies", "TV Shows", "Episodes"
        )

        val watchListTabPosition = 0
        val historyTabPosition = 1

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .alpha(
                            if (pagerState.currentPage == watchListTabPosition) {
                                1f
                            } else {
                                0.5f
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
                            if (pagerState.currentPage == historyTabPosition) {
                                1f
                            } else {
                                0.5f
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

            HorizontalPager(count = 2, state = pagerState, modifier = Modifier) { currentPage ->
                if (currentPage == watchListTabPosition) {
                    // Watchlist TAB
                    Column {

                        TabLayout(
                            tabData = watchlistTabData,
                            tabIndex = watchlistTabIndex,
                            pagerState = watchlistPagerState,
                            coroutineScope = coroutineScope
                        )

                        HorizontalPager(
                            count = watchListTabItems.size,
                            state = watchlistPagerState,
                            modifier = Modifier
                        ) { currentPage ->
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(sampleMovieList) {
                                    MovieExtendedCard(
                                        movie = it,
                                        onMovieClick = {},
                                        onOptionsClick = {},
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Divider(color = bottomNavigationContainerColor)
                                }
                            }
                        }
                    }
                } else {
                    // History TAB
                    Column {
                        TabLayout(
                            tabData = historyTabData,
                            tabIndex = historyTabIndex,
                            pagerState = historyPagerState,
                            coroutineScope = coroutineScope
                        )

                        HorizontalPager(
                            count = historyListTabItems.size,
                            state = historyPagerState,
                            modifier = Modifier
                        ) { currentPage ->
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = historyListTabItems[currentPage].title,
                                    style = MaterialTheme.typography.bodySmall
                                )

                            }

                        }
                    }

                }
            }
        }


    }
}


