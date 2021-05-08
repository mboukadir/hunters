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

package com.mb.hunters.ui.home.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.repository.collection.CollectionPagingRepository
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.common.SingleLiveEvent
import com.mb.hunters.ui.home.collection.model.CollectionMapper
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    mapper: CollectionMapper,
    collectionRepository: CollectionRepository,
    pagingRepository: CollectionPagingRepository
) : BaseViewModel(dispatchersProvider) {

    val errorMessage = SingleLiveEvent<String>()
    private val _collections = MutableLiveData<List<CollectionUiModel>>()
    val collections: LiveData<List<CollectionUiModel>> = _collections

    val collectionsFlow = pagingRepository.getCollections().map {
        it.map { collection ->
            mapper.mapToUiModel(collection)
        }
    }.cachedIn(viewModelScope)

    init {

        viewModelScope.launch {
            _collections.value = mapper.mapToUiModel(collectionRepository.getCollections())
        }
    }

    fun onClicked() {
    }
}
