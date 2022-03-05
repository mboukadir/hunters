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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mb.hunters.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.mb.hunters.R
import com.mb.hunters.ui.home.collection.CollectionsScreen
import com.mb.hunters.ui.home.posts.PostsScreen
import com.mb.hunters.ui.post.detail.PostDetailScreen
import com.mb.hunters.ui.post.poster.PostPosterScreen
import com.mb.hunters.ui.post.state.PosterItemsState

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        homeGraph(
            navController = navController,
            startDestination = Screen.Home.Posts.route,
            route = Screen.Home.ROUTE
        )

        postDetailGraph(
            navController = navController,
            startDestination = "${Screen.Post.Detail.route}/{${Screen.Post.POST_ID_KEY}}",
            route = "${Screen.Post.ROUTE}/{${Screen.Post.POST_ID_KEY}}",
            arguments = listOf(
                navArgument(Screen.Post.POST_ID_KEY) { type = NavType.LongType }
            )
        )
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    startDestination: String,
    route: String
) {
    navigation(startDestination = startDestination, route = route) {
        composable(Screen.Home.Posts.route) { PostsScreen(navController, hiltViewModel()) }
        composable(Screen.Home.Collections.route) { CollectionsScreen(hiltViewModel()) }
    }
}

private fun NavGraphBuilder.postDetailGraph(
    navController: NavHostController,
    startDestination: String,
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
) {
    navigation(startDestination = startDestination, route = route, arguments = arguments) {
        composable(
            route = startDestination,
            arguments = arguments
        ) {
            PostDetailScreen(
                navController = navController,
                postDetailViewModel = hiltViewModel(),
                modifier = Modifier
            )
        }
        composable(
            route = Screen.Post.Poster.route,
        ) {
            val posterItemsState = navController.previousBackStackEntry
                ?.arguments?.getParcelable<PosterItemsState>(Screen.Post.POST_POSTER_KEY)

            PostPosterScreen(
                navController = navController,
                items = posterItemsState?.items
            )
        }
    }
}

sealed class Screen(val route: String) {

    sealed class Home(
        route: String,
        @StringRes val title: Int,
        @DrawableRes val icon: Int
    ) : Screen(route) {
        companion object {
            const val ROUTE = "home"
        }

        object Posts : Home("posts", R.string.home_nav_posts, R.drawable.ic_popular)
        object Collections :
            Home("collections", R.string.home_nav_collection, R.drawable.ic_collection_24px)
    }

    sealed class Post(route: String) : Screen(route) {
        companion object {
            const val ROUTE = "post"
            const val POST_ID_KEY = "POST_ID.KEY"
            const val POST_POSTER_KEY = "POST_POSTER.KEY"
        }

        object Detail : Post("detail")
        object Poster : Post("poster")
    }
}
