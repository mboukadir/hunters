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

import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.api.model.CollectionResponse
import com.mb.hunters.data.repository.collection.remote.CollectionRemoteDataSource
import kotlinx.coroutines.withContext

class CollectionDataRepository(
    private val remoteDataSource: CollectionRemoteDataSource,
    private val dispatchersProvider: DispatchersProvider
) : CollectionRepository {
    override suspend fun getCollections(): List<Collection> =
        withContext(dispatchersProvider.computation) {
            mapToCollectionEntityList(remoteDataSource.getCollections())
        }

    private fun mapToCollectionEntityList(
        collectionList: List<CollectionResponse>
    ): List<Collection> {

        return collectionList.map {
            Collection(
                id = it.id,
                name = it.name,
                title = it.title.orEmpty(),
                backgroundImageUrl = it.backgroundImageUrl ?: "",
                collectionUrl = it.collectionUrl
            )
        }
    }
}
