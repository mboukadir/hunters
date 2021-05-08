package com.mb.hunters.data.repository.collection

import androidx.paging.*
import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.api.model.CollectionResponse
import java.io.IOException
import retrofit2.HttpException

class PageRemote constructor(
    private val collectionService: CollectionService
) : PagingSource<Int, Collection>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> = try {
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

    override fun getRefreshKey(state: PagingState<Int, Collection>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
