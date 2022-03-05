/*
 * Copyright 2022 Mohammed Boukadir
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

package com.mb.hunters.data.repository.post.model

import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val name: String,
    val tagline: String,
    val redirectUrl: String,
    val votesCount: Long,
    val createdAt: LocalDateTime,
    val screenshotUrl: String,
    val thumbnailUrl: String,
    val medias: List<Media>,
    val description: String,
    val topics: List<String>,
    val badge: Badge?,
    val hunter: User?,
    val makers: List<User>
)

data class User(
    val id: Long,
    val name: String,
    val imageUrl: String
)

data class Badge(
    val date: LocalDateTime,
    val period: String,
    val position: Int
)

sealed class Media {
    data class Image(
        val id: Long,
        val priority: Long,
        val url: String
    ) : Media()

    data class Video(
        val id: Long,
        val priority: Long,
        val previewUrl: String,
        val code: String,
        val url: String
    ) : Media()

    data class Audio(
        val id: Long,
        val priority: Long,
        val url: String
    ) : Media()
}
