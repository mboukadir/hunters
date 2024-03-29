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
