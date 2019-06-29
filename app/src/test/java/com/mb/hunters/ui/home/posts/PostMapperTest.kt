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

import com.mb.hunters.data.database.entity.PostEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class PostMapperTest {

    val postMapper = PostMapper()

    @Before
    fun setup() {
    }

    @Test
    fun shouldMapToUiModelReturnMappedUiModelForTodayPost() {

        val postUiModel = postMapper.mapToUiModel(POST)

        assertEquals(0, postUiModel.id)
        assertEquals("Name", postUiModel.title)
        assertEquals("TagLine", postUiModel.subTitle)
        assertEquals("redirectUrl", postUiModel.postUrl)
        assertEquals(1, postUiModel.votesCount)
        assertEquals(1, postUiModel.commentsCount)
        assertEquals("screenshotUrl", postUiModel.bigImageUrl)
        assertEquals("thumbnailUrl", postUiModel.smallImageUrl)
        assertEquals(0, postUiModel.daysAgo)
        assertEquals(TODAY.toString(), postUiModel.date)
    }

    @Test
    fun shouldMapToUiModelReturnMappedUiModelForYsterDayPost() {

        val postUiModel = postMapper.mapToUiModel(YESTERDAY_POST)

        assertEquals(0, postUiModel.id)
        assertEquals("Name", postUiModel.title)
        assertEquals("TagLine", postUiModel.subTitle)
        assertEquals("redirectUrl", postUiModel.postUrl)
        assertEquals(1, postUiModel.votesCount)
        assertEquals(1, postUiModel.commentsCount)
        assertEquals("screenshotUrl", postUiModel.bigImageUrl)
        assertEquals("thumbnailUrl", postUiModel.smallImageUrl)
        assertEquals(1, postUiModel.daysAgo)
        assertEquals(YESTERDAY.toString(), postUiModel.date)
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

        private val POST = PostEntity(
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