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

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.asLiveData
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.common.SingleLiveEvent
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CollectionsViewModel @ViewModelInject constructor(
    dispatchersProvider: DispatchersProvider,
    private val mapper: CollectionMapper,
    private val collectionRepository: CollectionRepository
) : BaseViewModel(dispatchersProvider) {

    val errorMessage = SingleLiveEvent<String>()
    val collections = collectionRepository.getCollections()
        .map { mapper.mapToUiModel(it) }
        .flowOn(dispatchersProvider.computation)
        .asLiveData(viewModelScope.coroutineContext)

    fun onClicked() {
    }

    fun syncCollections() {
        viewModelScope.launch {
            runCatching {
                collectionRepository.syncCollections()
            }.onFailure { throwable ->
                ensureActive().run {
                    errorMessage.value = throwable.message
                }
            }
        }
    }
}
