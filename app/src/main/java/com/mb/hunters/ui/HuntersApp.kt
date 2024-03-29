/*
 * Copyright 2022 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
