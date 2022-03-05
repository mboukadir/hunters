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

package com.mb.hunters.data.repository.collection

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.api.model.CollectionResponse
import com.mb.hunters.data.repository.collection.remote.CollectionRemoteDataSource
import com.mb.hunters.test.TestDispatcherProvider
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CollectionDataRepositoryTest {

    @Mock
    lateinit var remoteDataSource: CollectionRemoteDataSource

    lateinit var dataRepository: CollectionDataRepository

    @Before
    fun setup() {
        dataRepository =
            CollectionDataRepository(remoteDataSource, TestDispatcherProvider.dispatcherProvider)
    }

    @Test
    fun `Should return local collection when remote source return error`() {
        runBlockingTest {
            // GIVEN
            given(remoteDataSource.getCollections()).willReturn(COLLECTIONS)

            // WHEN
            val actual = dataRepository.getCollections()

            // THEN
            then(remoteDataSource).should().getCollections()
            assertThat(actual).containsExactlyElementsIn(COLLECTIONS_EXPECTED)
        }
    }

    companion object {

        val COLLECTION = CollectionResponse(
            id = 1,
            name = "name",
            title = "title",
            collectionUrl = "collectionUrl",
            backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTION_ENTITY = Collection(
            id = COLLECTION.id,
            name = COLLECTION.name,
            title = COLLECTION.title.orEmpty(),
            collectionUrl = COLLECTION.collectionUrl,
            backgroundImageUrl = COLLECTION.backgroundImageUrl ?: ""
        )

        val COLLECTIONS = listOf(
            COLLECTION,
            COLLECTION.copy(id = 2),
            COLLECTION.copy(id = 3),
            COLLECTION.copy(id = 4)
        )

        val COLLECTIONS_EXPECTED = listOf(
            COLLECTION_ENTITY,
            COLLECTION_ENTITY.copy(id = 2),
            COLLECTION_ENTITY.copy(id = 3),
            COLLECTION_ENTITY.copy(id = 4)

        )
    }
}
