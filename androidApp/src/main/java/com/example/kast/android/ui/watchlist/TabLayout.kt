package com.example.kast.android.ui.watchlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kast.android.theme.orange
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    tabData: List<String>,
    tabIndex: Int,
    pagerState: PagerState,
    coroutineScope : CoroutineScope
) {
    TabRow(
        selectedTabIndex = tabIndex,
        indicator = @Composable { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                color = orange
            )
        },
        modifier = Modifier.padding(top = 20.dp),
    ) {
        tabData.forEachIndexed { index, text ->
            Tab(selected = tabIndex == index, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                Text(text = text, style = MaterialTheme.typography.bodySmall)
            })
        }
    }
}


data class HorizontalPagerContent(
    val title: String,
)