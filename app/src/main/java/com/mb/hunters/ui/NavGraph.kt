@file:OptIn(ExperimentalMaterial3Api::class)

package com.mb.hunters.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.mb.hunters.ui.postdetail.PostDetailScreen

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

        composable(
            route = "${Screen.PostDetail.route}/{${Screen.PostDetail.POST_ID_KEY}}",
            arguments = listOf(
                navArgument(Screen.PostDetail.POST_ID_KEY) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val postId = arguments.getLong(Screen.PostDetail.POST_ID_KEY)
            PostDetailScreen(postId, modifier)
        }
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

    object PostDetail : Screen("post") {
        const val POST_ID_KEY = "id"
    }
}
