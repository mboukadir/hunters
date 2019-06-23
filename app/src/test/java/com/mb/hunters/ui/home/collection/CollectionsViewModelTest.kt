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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mb.hunters.data.database.entity.CollectionEntity
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.test.TestSchedulerProvider
import com.mb.hunters.test.capture
import io.reactivex.Single
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CollectionsViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var collectionRepository: CollectionRepository
    @Mock lateinit var mapper: CollectionMapper
    @Captor lateinit var collectionEntityListCaptor: ArgumentCaptor<List<CollectionEntity>>
    @Captor lateinit var collectionUiModelListCaptor : ArgumentCaptor<List<CollectionUiModel>>
    @Mock lateinit var observer: Observer<List<CollectionUiModel>>

    private lateinit var collectionsViewModel: CollectionsViewModel

    @Before
    fun setup() {

        collectionsViewModel = CollectionsViewModel(TestSchedulerProvider, mapper, collectionRepository)

    }

    @Test
    fun loadCollections_ReturnListOfCollectionUiModel() {

        `when`(collectionRepository.getCollections()).thenReturn(
                Single.just(COLLECTION_ENTITY_LIST))
        `when`(mapper.mapToUiModel(capture(collectionEntityListCaptor))).thenReturn(
                COLLECTION_MODEL_LIST)

        collectionsViewModel.collections.observeForever(observer)
        collectionsViewModel.loadCollections()

        verify(collectionRepository, times(1)).getCollections()

        verify(mapper).mapToUiModel(capture(collectionEntityListCaptor))
        assertThat(collectionEntityListCaptor.value, equalTo(COLLECTION_ENTITY_LIST))

        verify(observer, times(1)).onChanged(capture(collectionUiModelListCaptor))
        assertThat(collectionUiModelListCaptor.value, equalTo(COLLECTION_MODEL_LIST))

    }

    companion object {

        val COLLECTION_ENTITY = CollectionEntity(
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