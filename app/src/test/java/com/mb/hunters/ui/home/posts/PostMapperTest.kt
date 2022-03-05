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

package com.mb.hunters.ui.home.posts

import com.google.common.truth.Truth
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.ui.home.posts.model.PostMapper
import com.mb.hunters.ui.home.posts.model.PostUiModel
import java.util.Calendar
import org.junit.Before
import org.junit.Test

class PostMapperTest {

    val postMapper = PostMapper()

    @Before
    fun setup() {
    }

    @Test
    fun shouldMapToUiModelReturnMappedUiModelForTodayPost() {

        val posts = listOf(POST)

        val postUiModel = postMapper.map(posts)

        Truth.assertThat(postUiModel).containsExactly(
            PostUiModel(
                id = 0,
                title = "Name",
                subTitle = "TagLine",
                postUrl = "redirectUrl",
                votesCount = 1,
                commentsCount = 1,
                date = TODAY.toString(),
                bigImageUrl = "screenshotUrl",
                smallImageUrl = "thumbnailUrl",
                daysAgo = 0
            )
        )
    }

    @Test
    fun shouldMapToUiModelReturnMappedUiModelForYsterDayPost() {

        val posts = listOf(YESTERDAY_POST)
        val postUiModel = postMapper.map(posts)

        Truth.assertThat(postUiModel).containsExactly(
            PostUiModel(
                id = 0,
                title = "Name",
                subTitle = "TagLine",
                postUrl = "redirectUrl",
                votesCount = 1,
                commentsCount = 1,
                date = YESTERDAY.toString(),
                bigImageUrl = "screenshotUrl",
                smallImageUrl = "thumbnailUrl",
                daysAgo = 1
            )
        )
    }

    companion object {

        private val TODAY = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time

        private val YESTERDAY = Calendar.getInstance().apply {
            time = TODAY
            // Move calendar to yesterday
            add(Calendar.DATE, -1)
        }.time

        private val POST = Post(
            id = 0,
            name = "Name",
            tagline = "TagLine",
            redirectUrl = "redirectUrl",
            votesCount = 1,
            commentsCount = 1,
            day = TODAY,
            createdAt = TODAY,
            thumbnailUrl = "thumbnailUrl",
            screenshotUrl = "screenshotUrl"
        )

        private val YESTERDAY_POST = POST.copy(day = YESTERDAY, createdAt = YESTERDAY)
    }
}
