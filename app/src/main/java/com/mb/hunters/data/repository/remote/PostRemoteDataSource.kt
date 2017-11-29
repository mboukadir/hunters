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

package com.mb.hunters.data.repository.remote

import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.api.model.Post
import com.mb.hunters.data.database.entity.PostEntity
import io.reactivex.Single

class PostRemoteDataSource(private val postService: PostService) {

    fun getPosts(page: Long): Single<List<PostEntity>> {

        return postService.getPostsBy(page)
                .map { mapToEntity(it.posts) }

    }

    private fun mapToEntity(posts: List<Post>): List<PostEntity> {
        return posts.map {
            PostEntity(it.id,
                    it.name,
                    it.tagline,
                    it.redirectUrl,
                    it.votesCount,
                    it.commentsCount,
                    it.day,
                    it.createdAt,
                    it.screenshotUrl.smallImgUrl,
                    it.thumbnail.imageUrl)
        }
    }
}