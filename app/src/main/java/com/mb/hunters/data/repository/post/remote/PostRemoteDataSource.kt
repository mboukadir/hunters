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

package com.mb.hunters.data.repository.post.remote

import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import kotlinx.coroutines.withContext

class PostRemoteDataSource(
    private val postService: PostService,
    private val converter: PostsResponseConverter,
    private val dispatchersProvider: DispatchersProvider
) {

    suspend fun getPosts(page: Long): List<Post> = withContext(dispatchersProvider.computation) {
        converter.convert(postService.getPostsBy(page))
    }

    suspend fun getPost(id: Long): PostDetail = withContext(dispatchersProvider.computation) {
        converter.convert(postService.getPostBy(id).post)
    }
}
