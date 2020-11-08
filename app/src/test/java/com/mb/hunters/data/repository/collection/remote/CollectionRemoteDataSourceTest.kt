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

package com.mb.hunters.data.repository.collection.remote

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.api.model.Collection
import com.mb.hunters.data.api.model.CollectionsResponse
import com.mb.hunters.test.TestDispatcherProvider
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CollectionRemoteDataSourceTest {

    lateinit var collectionRemoteDataSource: CollectionRemoteDataSource
    @Mock lateinit var collectionService: CollectionService

    @Before
    fun setup() {

        collectionRemoteDataSource = CollectionRemoteDataSource(collectionService, TestDispatcherProvider.dispatcherProvider)
    }

    @Test
    fun getCollectionsReturnEmptyCollectionList() = runBlockingTest {

        given(collectionService.getCollections()).willReturn(COLLECTIONS_RESPONSE_EMPTY)

        val actual = collectionRemoteDataSource.getCollections()

        assertThat(actual).isEmpty()
    }

    @Test
    fun getCollectionsReturnCollectionList() = runBlockingTest {

        given(collectionService.getCollections()).willReturn(COLLECTIONS_RESPONSE)

        val actual = collectionRemoteDataSource.getCollections()

        assertThat(actual).containsExactlyElementsIn(COLLECTIONS)
    }

    companion object {

        val COLLECTIONS_RESPONSE_EMPTY = CollectionsResponse(emptyList())

        val COLLECTION = Collection(
            id = 1,
            name = "name",
            title = "title",
            collectionUrl = "collectionUrl",
            backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTIONS = listOf(
            COLLECTION,
            COLLECTION.copy(id = 2),
            COLLECTION.copy(id = 3),
            COLLECTION.copy(id = 4)
        )

        val COLLECTIONS_RESPONSE = CollectionsResponse(COLLECTIONS)
    }
}
