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
import android.arch.persistence.room.Room
import com.mb.hunters.data.api.ApiModule
import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.database.HuntersDatabase
import com.mb.hunters.data.repository.PostRepository
import com.mb.hunters.data.repository.local.PostLocalDataSource
import com.mb.hunters.data.repository.remote.PostRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(
        ApiModule::class
))
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): HuntersDatabase {
        return Room.databaseBuilder(application, HuntersDatabase::class.java, "database")
                .build()
    }

    @Provides
    @Singleton
    fun providePostRepository(postService: PostService,
            huntersDatabase: HuntersDatabase): PostRepository {
        return PostRepository(PostRemoteDataSource(postService),
                PostLocalDataSource(huntersDatabase.postDao()))
    }

}