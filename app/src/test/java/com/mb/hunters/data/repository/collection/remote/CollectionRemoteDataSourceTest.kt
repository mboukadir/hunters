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

import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.api.model.Collection
import com.mb.hunters.data.api.model.CollectionsResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

@RunWith(MockitoJUnitRunner::class)
class CollectionRemoteDataSourceTest {

    lateinit var collectionRemoteDataSource: CollectionRemoteDataSource
    @Mock lateinit var collectionService: CollectionService

    @Before
    fun setup() {

        collectionRemoteDataSource = CollectionRemoteDataSource(collectionService)
    }

    @Test
    fun getCollectionsReturnEmptyCollectionList() {

        `when`(collectionService.getCollections()).thenReturn(Single.just(resultOf(COLLECTIONS_RESPONSE_EMPTY)))

        collectionRemoteDataSource.getCollections()
                .test()
                .assertNoErrors()
                .assertValue(emptyList())
                .assertComplete()

    }

    @Test
    fun getCollectionsReturnCollectionList() {

        `when`(collectionService.getCollections()).thenReturn(Single.just(resultOf(COLLECTIONS_RESPONSE)))

        collectionRemoteDataSource.getCollections()
                .test()
                .assertNoErrors()
                .assertValue({
                    it.contains(COLLECTION)
                })
                .assertComplete()

    }

    companion object {

        fun <T> resultOf(response: T): Result<T> = Result.response(Response.success(response))

        val COLLECTIONS_RESPONSE_EMPTY = CollectionsResponse(emptyList())

        val COLLECTION = Collection(
                id = 1,
                name = "name",
                title = "title",
                collectionUrl = "collectionUrl",
                backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTIONS = listOf(COLLECTION,
                COLLECTION.copy(id=2),
                COLLECTION.copy(id=3),
                COLLECTION.copy(id=4)
        )

        val COLLECTIONS_RESPONSE = CollectionsResponse(COLLECTIONS)

    }

}