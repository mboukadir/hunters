package com.mb.hunters.ui.home.collection

import com.mb.hunters.data.database.entity.CollectionEntity
import com.mb.hunters.ui.home.collection.CollectionsViewModelTest.Companion
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class CollectionMapperTest {

    val collectionMapper = CollectionMapper()

    @Test
    fun mapCollectionEntityToCollectionUiModel() {

        val result = collectionMapper.mapToUiModel(COLLECTION_ENTITY)

        assertThat(result, equalTo(COLLECTION_MODEL))

    }

    @Test
    fun mapCollectionEntityListToCollectionUiModelList() {

        val result = collectionMapper.mapToUiModel(COLLECTION_ENTITY_LIST)

        assertThat(result, equalTo(COLLECTION_MODEL_LIST))

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
                CollectionsViewModelTest.COLLECTION_ENTITY,
                CollectionsViewModelTest.COLLECTION_ENTITY.copy(id = 2)
        )

        val COLLECTION_MODEL_LIST = listOf(
                CollectionsViewModelTest.COLLECTION_MODEL,
                CollectionsViewModelTest.COLLECTION_MODEL.copy(id = 2)

        )

    }

}