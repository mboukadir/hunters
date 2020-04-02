package com.mb.hunters.data.repository.collection

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.database.entity.CollectionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionPagingRepository @Inject constructor(
    private val collectionService: CollectionService
) {
    fun getCollections(): Flow<PagingData<CollectionEntity>> = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ) {
        PageRemote(
            collectionService = collectionService
        )
    }.flow

}