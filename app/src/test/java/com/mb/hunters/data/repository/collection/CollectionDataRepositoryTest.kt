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

package com.mb.hunters.data.repository.collection

import com.mb.hunters.data.api.model.Collection
import com.mb.hunters.data.database.entity.CollectionEntity
import com.mb.hunters.data.repository.collection.local.CollectionLocalDataSource
import com.mb.hunters.data.repository.collection.remote.CollectionRemoteDataSource
import com.mb.hunters.test.any
import com.mb.hunters.test.capture
import io.reactivex.Single
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CollectionDataRepositoryTest {

    @Mock lateinit var localDataSource: CollectionLocalDataSource
    @Mock lateinit var remoteDataSource: CollectionRemoteDataSource
    @Captor lateinit var listEntityToSave: ArgumentCaptor<List<CollectionEntity>>

    lateinit var dataRepository: CollectionDataRepository

    @Before
    fun setup() {
        dataRepository = CollectionDataRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun getCollectionSaveAndReturnCollectionList() {

        `when`(remoteDataSource.getCollections()).thenReturn(Single.just(COLLECTIONS))

        dataRepository.getCollections()
                .test()
                .assertComplete()
                .assertValue({ it.containsAll(COLLECTIONS_ENTITY_EXPECTED) })

        verify(localDataSource, times(1)).save(capture(listEntityToSave))
        verify(localDataSource, never()).getCollections()

        assertThat(listEntityToSave.value, HasItems(COLLECTIONS_ENTITY_EXPECTED))

    }

    @Test
    fun getCollectionWhenApiCallReturnError_ReturnCollectionListFromLocal() {

        `when`(remoteDataSource.getCollections()).thenReturn(Single.error(Exception()))
        `when`(localDataSource.getCollections()).thenReturn(Single.just(COLLECTIONS_ENTITY_EXPECTED))

        dataRepository.getCollections()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue({ it.containsAll(COLLECTIONS_ENTITY_EXPECTED) })

        verify(localDataSource, never()).save(capture(listEntityToSave))

    }

    class HasItems<T>(private val list: T) : BaseMatcher<T>() where T : List<*> {
        override fun describeTo(description: Description?) {
        }

        override fun matches(item: Any?): Boolean {

            return list.containsAll((item as T))
        }

    }

    companion object {

        val COLLECTION = Collection(
                id = 1,
                name = "name",
                title = "title",
                collectionUrl = "collectionUrl",
                backgroundImageUrl = "backgroundImageUrl",
                posts = emptyList()
        )

        val COLLECTION_ENTITY = CollectionEntity(
                id = COLLECTION.id,
                name = COLLECTION.name,
                title = COLLECTION.title,
                collectionUrl = COLLECTION.collectionUrl,
                backgroundImageUrl = COLLECTION.backgroundImageUrl
        )

        val COLLECTIONS = listOf(COLLECTION,
                COLLECTION.copy(id = 2),
                COLLECTION.copy(id = 3),
                COLLECTION.copy(id = 4)
        )

        val COLLECTIONS_ENTITY_EXPECTED = listOf(
                COLLECTION_ENTITY,
                COLLECTION_ENTITY.copy(id = 2),
                COLLECTION_ENTITY.copy(id = 3),
                COLLECTION_ENTITY.copy(id = 4)

        )

    }

}