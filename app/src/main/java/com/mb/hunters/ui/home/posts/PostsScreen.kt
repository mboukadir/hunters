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

package com.mb.hunters.ui.home.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mb.hunters.ui.Screen
import com.mb.hunters.ui.home.posts.model.PostUiModel
import com.mb.hunters.ui.theme.ThemedPreview
import timber.log.Timber

@Composable
fun PostsScreen(
    navController: NavController,
    viewModel: PostsViewModel,
    modifier: Modifier = Modifier
) {
    val items by viewModel.posts.observeAsState(listOf())
    val isRefreshing by viewModel.isRefreshing
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.onRefresh() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        PostList(
            posts = items,
            modifier = modifier,
            onItemClicked = {
                navController.navigate("${Screen.Post.ROUTE}/${it.id}")
            },
            onNeedLoadMore = { viewModel.loadMore(it.daysAgo) }
        )
    }
}

@Composable
private fun PostList(
    posts: List<PostUiModel>,
    onNeedLoadMore: (lastElement: PostUiModel) -> Unit,
    modifier: Modifier = Modifier,
    onItemClicked: (PostUiModel) -> Unit = {},
) {
    var lastLoadedItemId by remember { mutableStateOf(-1L) }
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = posts) { index, post ->
            PostItem(
                postUiModel = post,
                modifier = Modifier.clickable {
                    onItemClicked(post)
                }
            )
            Divider(modifier = Modifier.fillMaxWidth())

            if (index == posts.lastIndex) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
                if (lastLoadedItemId != post.id) {
                    lastLoadedItemId = post.id
                    Timber.d("onNeedLoadMore ${post.title}")
                    onNeedLoadMore(post)
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_3_XL
)
@Composable
fun PostListPreview() {
    val id = 1L
    val postUiModel = PostUiModel(
        id = id,
        title = "title $id",
        subTitle = "Subtitle$id",
        postUrl = "url",
        votesCount = id,
        commentsCount = id,
        daysAgo = id,
        date = "Date $id",
        bigImageUrl = "imageUrl",
        smallImageUrl = "smallImageUrl"
    )
    ThemedPreview {
        PostList(listOf(postUiModel), {})
    }
}
