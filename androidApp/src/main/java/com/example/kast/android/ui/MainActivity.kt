package com.example.kast.android.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kast.android.theme.KastTheme
import com.example.kast.android.theme.background
import com.example.kast.android.theme.bottomNavigationContainerColor
import com.example.kast.android.ui.components.AppBottomNavigationBar
import com.example.kast.android.utils.SetDarkSystemBarColors
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KastContent()
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class
)
@Composable
fun KastContent() {
    KastTheme(darkTheme = true) {
//        val state by remember {
//            viewModel.state
//        }
        SetDarkSystemBarColors(background, bottomNavigationContainerColor)

//        val navController = rememberNavController()
//        val bottomSheetState = rememberBottomSheetScaffoldState()
//        var bottomSheetTitle by remember { mutableStateOf("") }

        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberAnimatedNavController(bottomSheetNavigator)
        val navBackStackEntry by navController.currentBackStackEntryAsState()

//        BottomSheetScaffold(
//            scaffoldState = bottomSheetState,
//            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
//            sheetBackgroundColor = background,
//            sheetPeekHeight = 0.dp,
//            sheetContent = {
//                MovieMoreBottomSheet(bottomSheetTitle)
//            }
//        ) {

        Scaffold(
            bottomBar = {
                AppBottomNavigationBar(
                    currentDestination = navBackStackEntry?.destination,
                    navigateToTopLevelDestination = { route ->
                        navController.navigate(route.route) {
                            restoreState = true
                        }
                    })
            }
        ) { scaffoldPadding ->
            ModalBottomSheetLayout(bottomSheetNavigator) {
                AppNavigation(
                    navController,
                    modifier = Modifier.padding(scaffoldPadding)
                )
            }
        }
//        }
    }
}



@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KastContent()
}


