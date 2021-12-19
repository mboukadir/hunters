package com.mb.hunters.ui.home.collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mb.hunters.ui.home.collection.model.CollectionUiModel

@Composable
fun CollectionsScreen(
    viewModel: CollectionsViewModel
) {
    val items by viewModel.collections.observeAsState(listOf())
    CollectionList(
        items
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionList(
    items: List<CollectionUiModel>
) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(items) { collectionUiModel ->
            CollectionItem(collectionUiModel = collectionUiModel)
        }
    }
}
