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

package com.mb.hunters.data.repository.post.remote

import com.mb.hunters.data.api.model.BadgeResponse
import com.mb.hunters.data.api.model.MediaResponse
import com.mb.hunters.data.api.model.MediaTypeResponse
import com.mb.hunters.data.api.model.PostDetailResponse
import com.mb.hunters.data.api.model.PostsResponse
import com.mb.hunters.data.repository.post.model.Badge
import com.mb.hunters.data.repository.post.model.Media
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.data.repository.post.model.User
import com.mb.hunters.ui.common.extensions.toLocalDateTime
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class PostsResponseConverter @Inject constructor() {
    fun convert(postsResponse: PostsResponse): List<Post> {
        return postsResponse.postResponses.map {
            Post(
                it.id,
                it.name,
                it.tagline,
                it.redirectUrl,
                it.votesCount,
                it.commentsCount,
                it.day,
                it.createdAt,
                it.screenshotUrl.smallImgUrl,
                it.thumbnail.imageURL
            )
        }
    }

    fun convert(postDetailResponse: PostDetailResponse): PostDetail {
        return PostDetail(
            id = postDetailResponse.id,
            name = postDetailResponse.name,
            tagline = postDetailResponse.tagline,
            redirectUrl = postDetailResponse.redirectURL,
            votesCount = postDetailResponse.votesCount,
            createdAt = postDetailResponse.createdAt.toLocalDateTime(),
            screenshotUrl = postDetailResponse.screenshotURL.smallImgUrl,
            thumbnailUrl = postDetailResponse.thumbnail.imageURL,
            medias = postDetailResponse.media.toMedias(),
            description = postDetailResponse.description,
            topics = postDetailResponse.topics.map { it.name },
            badge = postDetailResponse.badges.resolveTopBadge(),
            hunter = postDetailResponse.resolveHunter(),
            makers = postDetailResponse.makers.map {
                User(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageURL.the48Px ?: it.imageURL.original.orEmpty()
                )
            }
        )
    }

    private fun PostDetailResponse.resolveHunter() =
        if (this.makerInside)
            User(
                id = user.id,
                name = user.name,
                imageUrl = user.imageURL.the48Px ?: user.imageURL.original.orEmpty()
            )
        else
            null

    private fun List<MediaResponse>.toMedias(): List<Media> {
        return this.map {
            when (it.mediaType) {
                MediaTypeResponse.IMAGE -> Media.Image(
                    id = it.id,
                    priority = it.priority,
                    url = it.imageURL
                )
                MediaTypeResponse.VIDEO -> Media.Video(
                    id = it.id,
                    priority = it.priority,
                    previewUrl = it.imageURL,
                    code = it.videoID.orEmpty(),
                    url = it.imageURL
                )
                MediaTypeResponse.AUDIO -> Media.Audio(
                    id = it.id,
                    priority = it.priority,
                    url = it.imageURL
                )
            }
        }
    }

    private fun List<BadgeResponse>.resolveTopBadge(): Badge? =
        this.firstOrNull { it.type == "Badges::TopPostBadge" }?.let {
            Badge(
                date = Instant.ofEpochMilli(it.data.date.time)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
                period = it.data.period,
                position = it.data.position
            )
        }
}
