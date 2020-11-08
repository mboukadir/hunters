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

package com.mb.hunters.data.repository.post

import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.data.repository.post.local.PostLocalDataSource
import com.mb.hunters.data.repository.post.remote.PostRemoteDataSource
import kotlinx.coroutines.*

class PostRepositoryData(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val postLocalDataSource: PostLocalDataSource,
    private val dispatchersProvider: DispatchersProvider
) : PostRepository {

    override suspend fun loadPosts(daysAgo: Long): List<PostEntity> = withContext(dispatchersProvider.computation) {
        runCatching {
            postRemoteDataSource.getPosts(daysAgo).also {
                postLocalDataSource.savePosts(it)
            }
        }.getOrElse {
            ensureActive().run {
                postLocalDataSource.getPostsAtDaysAgoOrOlder(daysAgo)
            }
        }
    }

    override suspend fun refreshPosts(daysAgo: Long): List<PostEntity> {
        return postRemoteDataSource.getPosts(daysAgo).also {
            postLocalDataSource.savePosts(it)
        }
    }
}
