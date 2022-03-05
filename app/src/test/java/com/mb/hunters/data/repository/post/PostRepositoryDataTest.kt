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

package com.mb.hunters.data.repository.post

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.data.repository.post.remote.PostRemoteDataSource
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostRepositoryDataTest {

    @Mock
    lateinit var postRemoteDataSource: PostRemoteDataSource

    private lateinit var postRepository: PostRepositoryData

    @Before
    fun setup() {
        postRepository = PostRepositoryData(
            postRemoteDataSource,
        )
    }

    @Test
    fun `Should load posts from remote `() = runTest {
        // GIVEN
        val expected = mock<List<Post>>()
        given(postRemoteDataSource.getPosts(0)).willReturn(expected)

        // WHEN
        val actual = postRepository.loadPosts(0)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should load detail post`() = runTest {
        // GIVEN
        val id = 1L
        val expected = mock<PostDetail>()
        given(postRemoteDataSource.getPost(id)).willReturn(expected)

        // WHEN
        val actual = postRepository.getPost(id)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
