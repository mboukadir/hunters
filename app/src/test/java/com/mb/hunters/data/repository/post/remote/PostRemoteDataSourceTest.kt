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

package com.mb.hunters.data.repository.post.remote

import com.google.common.truth.Truth
import com.mb.hunters.data.api.PostService
import com.mb.hunters.data.api.model.PostsResponse
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.test.TestDispatcherProvider
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostRemoteDataSourceTest {

    @Mock
    lateinit var service: PostService
    @Mock
    lateinit var converter: PostsResponseConverter
    lateinit var postRemoteDataSource: PostRemoteDataSource

    @Before
    fun setup() {
        postRemoteDataSource = PostRemoteDataSource(service, converter, TestDispatcherProvider.dispatcherProvider)
    }

    @Test
    fun `Should return post entity list`() = runBlockingTest {
        // GIVEN
        val postsResponse = mock<PostsResponse>()
        val postEntitiesExpected = mock<List<PostEntity>>()

        given(service.getPostsBy(0)).willReturn(postsResponse)
        given(converter.convert(postsResponse)).willReturn(postEntitiesExpected)

        // WHEN
        val actual = postRemoteDataSource.getPosts(0)

        // THEN
        Truth.assertThat(actual).isEqualTo(postEntitiesExpected)
    }
}
