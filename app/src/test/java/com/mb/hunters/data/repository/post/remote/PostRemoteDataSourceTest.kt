/*
 * Copyright 2017-2022 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mb.hunters.data.repository.post.remote

import com.google.common.truth.Truth.*
import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.api.model.PostDetailResponse
import com.mb.hunters.data.api.model.PostDetailResponseEnvelope
import com.mb.hunters.data.api.model.PostsResponse
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.test.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PostRemoteDataSourceTest {

    @Mock
    lateinit var service: PostService

    @Mock
    lateinit var converter: PostsResponseConverter

    private lateinit var subject: PostRemoteDataSource

    @Before
    fun setup() {
        subject =
            PostRemoteDataSource(
                postService = service,
                converter = converter,
                dispatchersProvider =
                TestDispatcherProvider.dispatcherProvider
            )
    }

    @Test
    fun `Should return post  list`() = runTest {
        // GIVEN
        val page = 0L
        val postsResponse = mock<PostsResponse>()
        val expected = mock<List<Post>>()

        given(service.getPostsBy(page)).willReturn(postsResponse)
        given(converter.convert(postsResponse)).willReturn(expected)

        // WHEN
        val actual = subject.getPosts(page)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail`() = runTest {
        // GIVEN
        val postId = 1L
        val postDetailResponse = mock<PostDetailResponse>()
        val expected = mock<PostDetail>()

        given(service.getPostBy(postId)).willReturn(PostDetailResponseEnvelope(postDetailResponse))
        given(converter.convert(postDetailResponse)).willReturn(expected)

        // WHEN
        val actual = subject.getPost(postId)

        // THEN
        assertThat(actual).isEqualTo(expected)
    }
}
