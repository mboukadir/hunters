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
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@UseExperimental(ExperimentalCoroutinesApi::class)
class CollectionDataRepositoryTest {

    @Mock
    lateinit var localDataSource: CollectionLocalDataSource
    @Mock
    lateinit var remoteDataSource: CollectionRemoteDataSource

    lateinit var dataRepository: CollectionDataRepository

    @Before
    fun setup() {
        dataRepository = CollectionDataRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Should save and return collection list received from remote data`() = runBlockingTest {
        // GIVEN
        given(remoteDataSource.getCollections()).willReturn(Single.just(COLLECTIONS))

        // WHEN
        val testObserver = dataRepository.getCollections().test()

        // THEN
        then(localDataSource).should().save(COLLECTIONS_ENTITY_EXPECTED)

        testObserver.assertComplete().assertValue {
            it.containsAll(COLLECTIONS_ENTITY_EXPECTED)
        }
    }

    @Test
    fun `Should return local collection when remote source return error`() = runBlockingTest {
        // GIVEN
        given(remoteDataSource.getCollections()).willReturn(Single.error(Exception()))
        given(localDataSource.getCollections()).willReturn(COLLECTIONS_ENTITY_EXPECTED)

        // WHEN
        val testObserver = dataRepository.getCollections().test()

        // THEN
        then(localDataSource).should().getCollections()

        testObserver.assertNoErrors().assertComplete().assertValue {
            it.containsAll(COLLECTIONS_ENTITY_EXPECTED)
        }
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
            backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTION_ENTITY = CollectionEntity(
            id = COLLECTION.id,
            name = COLLECTION.name,
            title = COLLECTION.title,
            collectionUrl = COLLECTION.collectionUrl,
            backgroundImageUrl = COLLECTION.backgroundImageUrl ?: ""
        )

        val COLLECTIONS = listOf(
            COLLECTION,
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