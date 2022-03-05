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

package com.mb.hunters.data.repository.post

import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.data.repository.post.remote.PostRemoteDataSource

class PostRepositoryData(
    private val postRemoteDataSource: PostRemoteDataSource,
) : PostRepository {

    override suspend fun loadPosts(daysAgo: Long): List<Post> =
        postRemoteDataSource.getPosts(daysAgo)

    override suspend fun refreshPosts(daysAgo: Long): List<Post> {
        return postRemoteDataSource.getPosts(daysAgo)
    }

    override suspend fun getPost(id: Long): PostDetail {
        return postRemoteDataSource.getPost(id)
    }
}
