package com.mb.hunters.ui.home.collection

import com.mb.hunters.data.database.entity.CollectionEntity
import javax.inject.Inject

class CollectionMapper @Inject constructor() {

    fun mapToUiModel(collectionEntityList: List<CollectionEntity>) =
            collectionEntityList.map { mapToUiModel(it) }


    fun mapToUiModel(collectionEntity: CollectionEntity) = CollectionUiModel(
            id = collectionEntity.id,
            name = collectionEntity.name,
            title = collectionEntity.title,
            collectionUrl = collectionEntity.collectionUrl,
            backgroundImageUrl = collectionEntity.backgroundImageUrl)

}

