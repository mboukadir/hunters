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

import com.mb.hunters.data.database.dao.PostDao
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.ui.common.extensions.dateAt
import java.util.*

class PostLocalDataSource(private val postDao: PostDao) {

    suspend fun savePosts(posts: List<PostEntity>) {
        postDao.insert(posts)
    }

    /**
     * Load posts at given days ago.
     * If there are no posts for the given day we are looking for posts from the day before
     */

    suspend fun getPostsAtDaysAgoOrOlder(daysAgo: Long): List<PostEntity> {
        val date = dateAt(daysAgo)
        val localPost = postDao.getPosts(date)
        // If there are no posts for the given day we are looking for posts from the day before
        return if (localPost.isEmpty() && existPostsBefore(date)) {
            getPostsAtDaysAgoOrOlder(daysAgo + 1)
        } else {
            localPost
        }
    }

    private suspend fun existPostsBefore(date: Date) = postDao.countPostOlderThan(date) > 0
}
