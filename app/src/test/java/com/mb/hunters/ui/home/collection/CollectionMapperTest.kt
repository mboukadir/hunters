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

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.repository.collection.Collection
import com.mb.hunters.ui.home.collection.model.CollectionMapper
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import org.junit.Test

class CollectionMapperTest {

    val collectionMapper = CollectionMapper()

    @Test
    fun mapCollectionEntityToCollectionUiModel() {

        val result = collectionMapper.mapToUiModel(COLLECTION_ENTITY)

        assertThat(result).isEqualTo(COLLECTION_MODEL)
    }

    @Test
    fun mapCollectionEntityListToCollectionUiModelList() {

        val result = collectionMapper.mapToUiModel(COLLECTION_ENTITY_LIST)

        assertThat(result).containsExactlyElementsIn(COLLECTION_MODEL_LIST)
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
            CollectionsViewModelTest.COLLECTION_ENTITY,
            CollectionsViewModelTest.COLLECTION_ENTITY.copy(id = 2)
        )

        val COLLECTION_MODEL_LIST = listOf(
            CollectionsViewModelTest.COLLECTION_MODEL,
            CollectionsViewModelTest.COLLECTION_MODEL.copy(id = 2)

        )
    }
}
