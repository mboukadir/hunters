package com.mb.hunters.data.repository.collection

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mb.hunters.data.api.CollectionService
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class CollectionPagingRepository @Inject constructor(
    private val collectionService: CollectionService
) {
    fun getCollections(): Flow<PagingData<Collection>> = Pager(
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
