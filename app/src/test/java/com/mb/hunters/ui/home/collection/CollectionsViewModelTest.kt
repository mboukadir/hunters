/*
 * Copyright 2017-2022 Mohammed Boukadir
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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mb.hunters.data.repository.collection.Collection
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.test.MainCoroutineRule
import com.mb.hunters.test.TestDispatcherProvider
import com.mb.hunters.ui.home.collection.model.CollectionMapper
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CollectionsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var collectionRepository: CollectionRepository
    @Mock
    lateinit var mapper: CollectionMapper

    @Test
    fun `Should display collections list with success`() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        given(collectionRepository.getCollections()).willReturn(COLLECTION_ENTITY_LIST)
        given(mapper.mapToUiModel(COLLECTION_ENTITY_LIST)).willReturn(COLLECTION_MODEL_LIST)
        val observer = mock<Observer<List<CollectionUiModel>>>()

        val collectionsViewModel = CollectionsViewModel(
            TestDispatcherProvider.dispatcherProvider,
            mapper,
            collectionRepository
        )

        // WHEN
        collectionsViewModel.collections.observeForever(observer)

        // THEN
        then(observer).should().onChanged(COLLECTION_MODEL_LIST)
    }

    companion object {

        val COLLECTION_ENTITY = Collection(
            id = 1,
            name = "name",
            title = "title",
            collectionUrl = "collectionUrl",
            backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTION_MODEL = CollectionUiModel(
            id = COLLECTION_ENTITY.id,
            name = COLLECTION_ENTITY.name,
            title = COLLECTION_ENTITY.title,
            collectionUrl = COLLECTION_ENTITY.collectionUrl,
            backgroundImageUrl = COLLECTION_ENTITY.backgroundImageUrl
        )

        val COLLECTION_ENTITY_LIST = listOf(
            COLLECTION_ENTITY,
            COLLECTION_ENTITY.copy(id = 2)
        )

        val COLLECTION_MODEL_LIST = listOf(
            COLLECTION_MODEL,
            COLLECTION_MODEL.copy(id = 2)

        )
    }
}
