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

package com.mb.hunters.data.repository.collection.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.database.dao.CollectionDaoTest.Companion.COLLECTION
import com.mb.hunters.data.database.entity.CollectionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CollectionLocalDataSourceTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var collectionLocalDataRepository: CollectionLocalDataSource

    private lateinit var database: HuntersDatabase

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
                HuntersDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        collectionLocalDataRepository = CollectionLocalDataSource(database.collectionDao())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun saveAndGetCollection() = runBlockingTest {

        // Given
        collectionLocalDataRepository.save(COLLECTIONS)

        // When
        val actual = collectionLocalDataRepository.getCollections()

        // Then
        assertThat(actual).containsExactlyElementsIn(COLLECTIONS)
    }

    companion object {
        val COLLECTION = CollectionEntity(
                id = 1,
                name = "name",
                title = "title",
                collectionUrl = "collectionUrl",
                backgroundImageUrl = "backgroundImageUrl"
        )

        val COLLECTIONS = listOf(COLLECTION,
                COLLECTION.copy(id = 2),
                COLLECTION.copy(id = 3),
                COLLECTION.copy(id = 4)
        )
    }
}