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

import com.google.common.truth.Truth
import com.mb.hunters.data.api.model.PostResponse
import com.mb.hunters.data.api.model.PostsResponse
import com.mb.hunters.data.api.model.ScreenshotUrl
import com.mb.hunters.data.api.model.Thumbnail
import com.mb.hunters.data.repository.post.Post
import java.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsResponseConverterTest {
    val converter = PostsResponseConverter()

    @Test
    fun `Should convert posts response to post entity list`() {
        // GIVEN
        val postesResponse = PostsResponse(listOf(postResponse))
        val postEntityListExpected = listOf(postEntityExpected)

        // WHEN
        val actual = converter.convert(postesResponse)

        // THEN
        Truth.assertThat(actual).containsExactlyElementsIn(postEntityListExpected)
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
            thumbnail = Thumbnail(
                id = 1,
                mediaType = "mediaType",
                imageUrl = "imageUrl"
            )
        )
        val postEntityExpected = Post(
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
