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

import androidx.lifecycle.MutableLiveData
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.ui.base.BaseViewModel
import com.mb.hunters.ui.common.SingleLiveEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val mapper: CollectionMapper,
    private val collectionRepository: CollectionRepository
) : BaseViewModel(dispatchersProvider) {

    val collections = MutableLiveData<List<CollectionUiModel>>()
    val errorMessage = SingleLiveEvent<String>()

    fun loadCollections() {

        viewModelScope.launch {
            try {
                val collectionUiList = withContext(dispatchersProvider.io) {

                    collectionRepository.getCollections()
                        .map { mapper.mapToUiModel(it) }
                        .await()
                }
                collections.value = collectionUiList
            } catch (e: Exception) {
                showErrorIfNeeded(e)
            }
        }
    }

    private fun showErrorIfNeeded(e: Exception) {
        if (e !is CancellationException) {
            errorMessage.value = e.message
        }
    }
}