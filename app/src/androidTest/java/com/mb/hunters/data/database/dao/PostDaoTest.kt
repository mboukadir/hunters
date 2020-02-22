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

package com.mb.hunters.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.*
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.database.entity.PostEntity
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date

@RunWith(AndroidJUnit4::class)
class PostDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: HuntersDatabase

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
                HuntersDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetPosts() = runBlockingTest {

        // GIVEN
        val posts = listOf(
                POST,
                POST.copy(id = 2),
                POST.copy(id = 3),
                POST.copy(id = 4, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 5, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 6, day = ONEDAY_BEFORE_YESTERDAY)
        )

        // When
        database.postDao().insert(posts)

        // Then
        val result = database.postDao()
                .getPosts(TODAY)

        assertThat(result).containsExactlyElementsIn(listOf(POST, POST.copy(id = 2), POST.copy(id = 3)))
    }

    @Test
    fun getCountWhenNotExistPostOlderThanGivenDate() = runBlockingTest {

        // GIVEN
        val posts = listOf(
                POST,
                POST.copy(id = 2),
                POST.copy(id = 3),
                POST.copy(id = 4, day = YESTERDAY),
                POST.copy(id = 5, day = YESTERDAY),
                POST.copy(id = 6, day = YESTERDAY)
        )

        // When
        database.postDao().insert(posts)

        // Then
        // Then
        val result = database.postDao()
                .countPostOlderThan(YESTERDAY)

        assertEquals(0, result)
    }

    @Test
    fun getCountWhenExistPostOlderThanGivenDate() = runBlockingTest {

        // GIVEN
        val posts = listOf(
                POST.copy(id = 1, day = TODAY),
                POST.copy(id = 2, day = TODAY),
                POST.copy(id = 3, day = TODAY),
                POST.copy(id = 4, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 5, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 6, day = ONEDAY_BEFORE_YESTERDAY)
        )

        // When
        database.postDao().insert(posts)

        // Then
        val restul = database.postDao()
                .countPostOlderThan(TODAY)

        assertEquals(3, restul)
    }

    private class PostDateMatcher(val date: Date) : BaseMatcher<PostEntity>() {
        override fun describeTo(description: Description?) {
        }

        override fun matches(item: Any?): Boolean {

            return (item as PostEntity).day == date
        }
    }

    companion object {
        private val TODAY = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time

        private val YESTERDAY = Calendar.getInstance().apply {
            time = TODAY
            add(Calendar.DATE, -1)
        }.time

        private val ONEDAY_BEFORE_YESTERDAY = Calendar.getInstance().apply {
            time = TODAY
            add(Calendar.DATE, -2)
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

        private val POSTS = listOf(
                POST,
                POST.copy(id = 2),
                POST.copy(id = 3),
                POST.copy(id = 4, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 5, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 6, day = ONEDAY_BEFORE_YESTERDAY)
        )
    }
}