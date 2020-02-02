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

package com.mb.hunters.data

import android.app.Application
import androidx.room.Room
import com.mb.hunters.common.dispatcher.DispatchersProvider
import com.mb.hunters.data.api.ApiModule
import com.mb.hunters.data.api.CollectionService
import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.repository.collection.CollectionDataRepository
import com.mb.hunters.data.repository.collection.CollectionRepository
import com.mb.hunters.data.repository.collection.local.CollectionLocalDataSource
import com.mb.hunters.data.repository.collection.remote.CollectionRemoteDataSource
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.data.repository.post.PostRepositoryData
import com.mb.hunters.data.repository.post.local.PostLocalDataSource
import com.mb.hunters.data.repository.post.remote.PostRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ApiModule::class)])
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): HuntersDatabase {
        return Room.databaseBuilder(application, HuntersDatabase::class.java, "database")
                .build()
    }

    @Provides
    @Singleton
    fun providePostRepository(
        postService: PostService,
        huntersDatabase: HuntersDatabase
    ): PostRepository {
        return PostRepositoryData(
                PostRemoteDataSource(postService),
                PostLocalDataSource(huntersDatabase.postDao())
        )
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(
        collectionService: CollectionService,
        huntersDatabase: HuntersDatabase,
        dispatchersProvider: DispatchersProvider
    ): CollectionRepository {

        return CollectionDataRepository(
                CollectionLocalDataSource(huntersDatabase.collectionDao()),
                CollectionRemoteDataSource(collectionService, dispatchersProvider),
                dispatchersProvider
        )
    }
}