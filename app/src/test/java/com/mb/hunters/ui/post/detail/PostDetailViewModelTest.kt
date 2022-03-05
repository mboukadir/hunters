/*
 * Copyright 2022 Mohammed Boukadir
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

package com.mb.hunters.ui.post.detail

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.data.repository.post.model.Post
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.test.TestDispatcherProvider
import com.mb.hunters.ui.Screen
import com.mb.hunters.ui.post.detail.PostDetailViewModel.PostDetailUiState
import com.mb.hunters.ui.post.state.PostDetailContentState
import com.mb.hunters.ui.post.state.PostDetailContentStateMapper
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostDetailViewModelTest {

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var mapper: PostDetailContentStateMapper

    @Mock
    lateinit var postRepository: PostRepository

    private val dispatchersProvider = TestDispatcherProvider.dispatcherProvider

    @Test
    fun `Should display loading and content`() = runTest {
        // GIVEN
        val postId = 1L
        val postDetail = mock<PostDetail>()
        val postDetailContentState = mock<PostDetailContentState>()

        given(savedStateHandle.get<Long>(Screen.Post.POST_ID_KEY)).willReturn(postId)
        given(mapper.map(postDetail)).willReturn(postDetailContentState)
        given(postRepository.getPost(postId)).willReturn(postDetail)

        val expectedLoading = PostDetailUiState.Loading
        val expectedSuccess = PostDetailUiState.Success(postDetailContentState)

        // WHEN
        val actualDiffered = async(UnconfinedTestDispatcher(testScheduler)) {
            val subject = newViewModel()
            subject.postDetailUiState.take(2).toList()
        }

        val actual = actualDiffered.await()

        // THEN
        assertThat(actual).containsExactly(expectedLoading, expectedSuccess).inOrder()
    }

    @Test
    fun `Should display loading and error when loading post failed`() = runTest {
        // GIVEN
        val postId = 1L
        val errorMsg = "Some thing going wrong"

        given(savedStateHandle.get<Long>(Screen.Post.POST_ID_KEY)).willReturn(postId)
        given(postRepository.getPost(postId)).willAnswer {
            throw Exception(errorMsg)
        }

        val expectedLoading = PostDetailUiState.Loading
        val expectedFailed = PostDetailUiState.Failed(errorMsg)

        // WHEN
        val actualDiffered = async(UnconfinedTestDispatcher(testScheduler)) {
            val subject = newViewModel()
            subject.postDetailUiState.take(2).toList()
        }

        val actual = actualDiffered.await()

        // THEN
        assertThat(actual).containsExactly(expectedLoading, expectedFailed).inOrder()
    }

    private fun newViewModel() = PostDetailViewModel(
        savedStateHandle = savedStateHandle,
        mapper = mapper,
        postRepository = postRepository,
        dispatchersProvider = dispatchersProvider
    )
}
