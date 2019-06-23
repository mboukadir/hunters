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
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.database.dao.CollectionDaoTest
import com.mb.hunters.data.database.entity.CollectionEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionLocalDataSourceTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var collectionLocalDataRepository: CollectionLocalDataSource

    private lateinit var database: HuntersDatabase

    @Before
    fun initDb() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
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
    fun saveAndGetCollection() {

        val collectionsToInsert = listOf(
                CollectionDaoTest.COLLECTION,
                CollectionDaoTest.COLLECTION.copy(id = 2),
                CollectionDaoTest.COLLECTION.copy(id = 3),
                CollectionDaoTest.COLLECTION.copy(id = 4)
        )

        //When

        collectionLocalDataRepository.save(collectionsToInsert)

        //Then

        collectionLocalDataRepository.getCollections()
                .test()
                .assertValue({
                    it.containsAll(CollectionDaoTest.COLLECTIONS)
                })
                .assertComplete()

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