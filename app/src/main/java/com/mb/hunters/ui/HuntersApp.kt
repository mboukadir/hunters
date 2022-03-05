package com.mb.hunters.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.mb.hunters.ui.home.HomeBottomBar
import com.mb.hunters.ui.home.HomeTopBar
import com.mb.hunters.ui.home.homeScreens
import com.mb.hunters.ui.theme.HuntersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuntersApp() {
    HuntersTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                HomeTopBar(
                    modifier = Modifier.statusBarsPadding(),
                    navController = navController
                )
            },
            bottomBar = {
                HomeBottomBar(
                    modifier = Modifier.navigationBarsPadding(),
                    navController = navController,
                    screens = homeScreens,
                )
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = Screen.Home.ROUTE
            )
        }
    }
}
