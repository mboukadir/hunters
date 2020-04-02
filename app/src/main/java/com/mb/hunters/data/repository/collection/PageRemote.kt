package com.mb.hunters.data.repository.collection

import androidx.paging.*
import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.api.model.Collection
import com.mb.hunters.data.database.entity.CollectionEntity
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PageRemote constructor(
    private val collectionService: CollectionService
) : PagingSource<Int, CollectionEntity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionEntity> = try {
        val pageNumber = params.key ?: 1
        val response = collectionService.getCollections(pageNumber, params.loadSize)
        val prevKey = if (pageNumber > 1) pageNumber - 1 else null
        val nextKey = if (response.collections.isNotEmpty()) pageNumber + 1 else null
        LoadResult.Page(
            data = mapToCollectionEntityList(response.collections),
            prevKey = prevKey,
            nextKey = nextKey
        )
    } catch (e: IOException) {
        LoadResult.Error(e)
    } catch (e: HttpException) {
        LoadResult.Error(e)
    }

    private fun mapToCollectionEntityList(
        collectionList: List<Collection>
    ): List<CollectionEntity> {

        return collectionList.map {
            CollectionEntity(
                id = it.id,
                name = it.name,
                title = it.title,
                backgroundImageUrl = it.backgroundImageUrl ?: "",
                collectionUrl = it.collectionUrl
            )
        }
    }


}