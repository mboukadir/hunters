package com.mb.hunters.ui.home.collection

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mb.hunters.ui.home.collection.model.CollectionUiModel

@Composable
fun CollectionsScreen() {
    val viewModel = viewModel<CollectionsViewModel>()
    val lazyPagingItems = viewModel.collectionsFlow.collectAsLazyPagingItems()
    CollectionsPaging(
        lazyPagingItems
    )
}

@Composable
fun CollectionsPaging(lazyPagingItems: LazyPagingItems<CollectionUiModel>) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
        items(lazyPagingItems) { collectionUiModel ->

            collectionUiModel?.let {
                CollectionItem(collectionUiModel = collectionUiModel)
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
