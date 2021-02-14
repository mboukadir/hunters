/*
 * Copyright 2017 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mb.hunters.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.primarySurface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.mb.hunters.ui.base.BaseActivity
import com.mb.hunters.ui.common.chromtab.CustomTabActivityHelper
import com.mb.hunters.ui.home.collection.CollectionsScreen
import com.mb.hunters.ui.home.posts.PostsScreen
import com.mb.hunters.ui.theme.HuntersTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val customTabActivityHelper = CustomTabActivityHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HuntersTheme {
                val (selectedTab, setSelectedTab) = remember { mutableStateOf(HomeTab.POSTS) }
                val tabs = HomeTab.values().asList()
                Scaffold(
                    backgroundColor = MaterialTheme.colors.primarySurface,
                    topBar = { HomeTopBar(modifier = Modifier.statusBarsPadding()) },
                    bottomBar = {
                        HomeBottomBar(
                            tabs = tabs,
                            modifier = Modifier.navigationBarsPadding(),
                            selectedTab = selectedTab,
                            onSelectedTab = { setSelectedTab(it) }
                        )
                    }
                ) { innerPadding ->
                    Crossfade(
                        targetState = selectedTab,
                        modifier = Modifier
                            .padding(innerPadding)
                    ) { tabs ->
                        when (tabs) {
                            HomeTab.POSTS -> PostsScreen()
                            HomeTab.COLLECTIONS -> CollectionsScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        super.onStop()
        customTabActivityHelper.unbindCustomTabsService(this)
    }
}
