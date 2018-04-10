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

package com.mb.hunters.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.mb.hunters.data.database.dao.CollectionDao
import com.mb.hunters.data.database.dao.PostDao
import com.mb.hunters.data.database.entity.CollectionEntity
import com.mb.hunters.data.database.entity.PostEntity

@Database(entities = [
    PostEntity::class,
    CollectionEntity::class
], version = 1)
@TypeConverters(DateConverter::class)
abstract class HuntersDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun collectionDao(): CollectionDao
}