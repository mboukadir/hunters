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

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.api.model.ImageURLResponse
import com.mb.hunters.data.api.model.MediaResponse
import com.mb.hunters.data.api.model.MediaTypeResponse
import com.mb.hunters.data.api.model.PostDetailResponse
import com.mb.hunters.data.api.model.PostResponse
import com.mb.hunters.data.api.model.PostsResponse
import com.mb.hunters.data.api.model.ScreenshotUrl
import com.mb.hunters.data.api.model.ThumbnailResponse
import com.mb.hunters.data.api.model.TopicResponse
import com.mb.hunters.data.api.model.UserResponse
import com.mb.hunters.data.repository.post.model.Media
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.data.repository.post.model.User
import com.mb.hunters.ui.common.extensions.toLocalDateTime
import java.util.Date
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsResponseConverterTest {
    private val subject = PostsResponseConverter()

    @Test
    fun `Should convert posts response to post entity list`() {
        // GIVEN
        val postsResponse = PostsResponse(listOf(postResponse))
        val postsExpected = listOf(postExpected)

        // WHEN
        val actual = subject.convert(postsResponse)

        // THEN
        assertThat(actual).containsExactlyElementsIn(postsExpected)
    }

    @Test
    fun `Should convert post detail response to post detail`() {
        // GIVEN
        val videoMediaResponse = MediaResponse(
            id = 1,
            priority = 1,
            originalHeight = 100,
            originalWidth = 100,
            mediaType = MediaTypeResponse.VIDEO,
            imageURL = "video media image url"
        )
        val imageMediaResponse = MediaResponse(
            id = 2,
            priority = 2,
            originalHeight = 100,
            originalWidth = 100,
            mediaType = MediaTypeResponse.IMAGE,
            imageURL = "image media image url"
        )

        val audioMediaResponse = MediaResponse(
            id = 3,
            priority = 3,
            originalHeight = 100,
            originalWidth = 100,
            mediaType = MediaTypeResponse.AUDIO,
            imageURL = "audio media image url"
        )

        val postDetailResponse = PostDetailResponse(
            id = 1,
            name = "name",
            tagline = "tagline",
            slug = "slug",
            votesCount = 2,
            createdAt = Date(),
            makerInside = false,
            makers = listOf(
                UserResponse(
                    id = 2,
                    createdAt = Date(),
                    name = "maker",
                    username = "makers name",
                    profileURL = "profileUrl",
                    imageURL = ImageURLResponse(the48Px = "")
                )
            ),
            redirectURL = "redirectUrl",
            screenshotURL = ScreenshotUrl(
                smallImgUrl = "smallImgUrl",
                largeImgUrl = "largeImgUrl"
            ),
            thumbnail = ThumbnailResponse(
                id = "id",
                imageURL = "ImageURL"
            ),
            topics = listOf(TopicResponse(id = 1, name = "topic name", slug = "topic slug")),
            user = UserResponse(
                id = 1,
                createdAt = Date(),
                name = "name",
                username = "username",
                profileURL = "profileUrl",
                imageURL = ImageURLResponse(the48Px = "")
            ),
            reviewsCount = 1,
            badges = emptyList(),
            comments = emptyList(),
            votes = emptyList(),
            media = listOf(videoMediaResponse, imageMediaResponse, audioMediaResponse),
            description = "description"
        )

        val expected = PostDetail(
            id = postDetailResponse.id,
            name = postDetailResponse.name,
            tagline = postDetailResponse.tagline,
            redirectUrl = postDetailResponse.redirectURL,
            votesCount = postDetailResponse.votesCount,
            createdAt = postDetailResponse.createdAt.toLocalDateTime(),
            screenshotUrl = postDetailResponse.screenshotURL.smallImgUrl,
            thumbnailUrl = postDetailResponse.thumbnail.imageURL,
            medias = listOf(
                Media.Video(
                    id = videoMediaResponse.id,
                    priority = videoMediaResponse.priority,
                    previewUrl = videoMediaResponse.imageURL,
                    code = "",
                    url = videoMediaResponse.imageURL
                ),
                Media.Image(
                    id = imageMediaResponse.id,
                    priority = imageMediaResponse.priority,
                    url = imageMediaResponse.imageURL
                ),
                Media.Audio(
                    id = audioMediaResponse.id,
                    priority = audioMediaResponse.priority,
                    url = audioMediaResponse.imageURL
                )
            ),
            description = postDetailResponse.description,
            topics = listOf("topic name"),
            badge = null,
            hunter = null,
            makers = listOf(
                User(
                    id = 2,
                    name = "maker",
                    imageUrl = ""
                )
            )
        )

        // WHEN
        val actual = subject.convert(postDetailResponse = postDetailResponse)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        private val day = Date()
        private val createdAt = Date()
        val postResponse = PostResponse(
            id = 1,
            name = "name",
            tagline = "tagline",
            redirectUrl = "redirectUrl",
            votesCount = 1,
            commentsCount = 2,
            day = day,
            createdAt = createdAt,
            screenshotUrl = ScreenshotUrl(
                smallImgUrl = "smallImgUrl",
                largeImgUrl = "largeImgUrl"
            ),
            thumbnail = ThumbnailResponse(
                id = "1",
                imageURL = "imageUrl"
            )
        )
        val postExpected = Post(
            id = 1,
            name = "name",
            tagline = "tagline",
            redirectUrl = "redirectUrl",
            votesCount = 1,
            commentsCount = 2,
            day = day,
            createdAt = createdAt,
            screenshotUrl = "smallImgUrl",
            thumbnailUrl = "imageUrl"
        )
    }
}
