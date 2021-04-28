package com.mb.hunters.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mb.hunters.R
import com.mb.hunters.ui.theme.ThemedPreview

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Hunters",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif

            )
        },
        contentColor = MaterialTheme.colors.secondary
    )
}

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    tabs: List<HomeTab>,
    selectedTab: HomeTab,
    onSelectedTab: (HomeTab) -> Unit
) {
    BottomNavigation(
        modifier = modifier,
        elevation = 0.dp
    ) {
        tabs.forEach { tab ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = stringResource(id = tab.title)
                    )
                },
                selected = tab == selectedTab,
                onClick = {
                    onSelectedTab(tab)
                },
                label = { Text(text = stringResource(id = tab.title).toUpperCase()) },
                alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colors.secondary
            )
        }
    }
}

enum class HomeTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    POSTS(R.string.home_nav_posts, R.drawable.ic_popular),
    COLLECTIONS(R.string.home_nav_collection, R.drawable.ic_collection_24px),
}

@Preview
@Composable
fun HomeTopBarPreview() {
    ThemedPreview {
        HomeTopBar()
    }
}

@Preview
@Composable
fun HomeBottomBarPreview() {
    ThemedPreview {
        HomeBottomBar(
            tabs = listOf(
                HomeTab.POSTS,
                HomeTab.COLLECTIONS
            ),
            selectedTab = HomeTab.POSTS
        ) {
        }
    }
}
