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

import com.google.common.truth.Truth.*
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.data.repository.post.local.PostLocalDataSource
import com.mb.hunters.data.repository.post.remote.PostRemoteDataSource
import com.mb.hunters.test.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostRepositoryDataTest {

    @Mock
    lateinit var postRemoteDataSource: PostRemoteDataSource
    @Mock
    lateinit var postLocalDataSource: PostLocalDataSource

    private lateinit var postRepository: PostRepositoryData

    @Before
    fun setup() {
        postRepository = PostRepositoryData(
            postRemoteDataSource,
            postLocalDataSource,
            TestDispatcherProvider.dispatcherProvider
        )
    }

    @Test
    fun `Should load posts from remote and save it in local cache`() = runBlockingTest {
        // GIVEN
        val expected = mock<List<PostEntity>>()
        given(postRemoteDataSource.getPosts(0)).willReturn(expected)

        // WHEN
        val actual = postRepository.loadPosts(0)

        // THEN
        assertThat(actual).isEqualTo(expected)
        then(postLocalDataSource).should().savePosts(expected)
    }

    @Test
    fun `Should load posts from local when load from remote failed`() = runBlockingTest {
        // GIVEN
        val expected = mock<List<PostEntity>>()
        given(postLocalDataSource.getPostsAtDaysAgoOrOlder(0)).willReturn(expected)
        given(postRemoteDataSource.getPosts(0)).willThrow(MockitoException(""))

        // WHEN
        val actual = postRepository.loadPosts(0)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should load post not load post from local when propagates Cancellation when job was canceled`() = runBlockingTest {
        // GIVEN
        val expected = mock<List<PostEntity>>()
        given(postRemoteDataSource.getPosts(0)).willReturn(expected)

        // WHEN
        withTimeout(1) {
            postRepository.loadPosts(0)
        }
        advanceTimeBy(1)
        // THEN
        then(postLocalDataSource).should(never()).getPostsAtDaysAgoOrOlder(0)
    }
}
