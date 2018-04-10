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

package com.mb.hunters.data.repository.post.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.ui.common.extensions.dateAt
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostLocalDataSourceTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var postLocalDataRepository: PostLocalDataSource

    private lateinit var database: HuntersDatabase

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                HuntersDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        postLocalDataRepository = PostLocalDataSource(database.postDao())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getNextDayPostWhenPostNotExistForGivenDay() {

        //GIVEN
        val posts = listOf(
                POST,
                POST.copy(id = 2),
                POST.copy(id = 3),
                POST.copy(id = 4, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 5, day = ONEDAY_BEFORE_YESTERDAY),
                POST.copy(id = 6, day = ONEDAY_BEFORE_YESTERDAY)
        )

        //When
        postLocalDataRepository.savePosts(posts)

        postLocalDataRepository.getPostsAtDaysAgoOrOlder(1)
                .test()
                .assertValue {
                    it.containsAll(POSTS)
                }

    }

    companion object {
        private val TODAY = dateAt(0)

        private val YESTERDAY = dateAt(1)

        private val ONEDAY_BEFORE_YESTERDAY = dateAt(2)

        private val TOWDAYS_BEFORE_YESTERDAY = dateAt(3)

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