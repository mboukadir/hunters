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

package com.mb.hunters.ui.home.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mb.hunters.data.repository.post.Post
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.test.TestDispatcherProvider.dispatcherProvider
import com.mb.hunters.ui.home.posts.model.PostMapper
import com.mb.hunters.ui.home.posts.model.PostUiModel
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var postRepository: PostRepository

    @Mock
    lateinit var mapper: PostMapper

    @Mock
    lateinit var observer: Observer<List<PostUiModel>>

    @Test
    fun `Should load to day posts and return list of ui post`() = runBlockingTest {
        // GIVEN
        val posts = mock<List<Post>>()
        val postUiModels = mock<List<PostUiModel>>()
        given(postRepository.loadPosts(0)).willReturn(posts)
        given(mapper.mapToUiModel(posts)).willReturn(postUiModels)

        val subject = newViewModel()

        // WHEN
        subject.posts.observeForever(observer)

        // THEN
        then(postRepository).should().loadPosts(0)
        then(observer).should().onChanged(postUiModels)
    }

    @Test
    fun `Should load more posts and return list of ui post`() = runBlockingTest {
        // GIVEN
        val postsToday = mock<List<Post>>()
        val postUiModelsToday = listOf<PostUiModel>(mock())
        val postsYesterday = mock<List<Post>>()
        val postUiModelsYesterday = listOf<PostUiModel>(mock())

        given(postRepository.loadPosts(0)).willReturn(postsToday)
        given(mapper.mapToUiModel(postsToday)).willReturn(postUiModelsToday)

        given(postRepository.loadPosts(1)).willReturn(postsYesterday)
        given(mapper.mapToUiModel(postsYesterday)).willReturn(postUiModelsYesterday)

        val subject = newViewModel()
        subject.posts.observeForever(observer)

        // WHEN
        subject.loadMore(0)

        // THEN
        then(observer).should().onChanged(postUiModelsToday + postUiModelsYesterday)
    }

    @Test
    fun `Should refresh today post and return list of ui post`() = runBlockingTest {
        // GIVEN
        val posts = mock<List<Post>>()
        val postUiModels = mock<List<PostUiModel>>()
        given(postRepository.loadPosts(0)).willReturn(posts)
        given(mapper.mapToUiModel(posts)).willReturn(postUiModels)

        val subject = newViewModel()
        subject.posts.observeForever(observer)

        // WHEN
        subject.refreshToDayPost()

        // THEN
        then(postRepository).should(times(2)).loadPosts(0)
        then(observer).should(times(2)).onChanged(postUiModels)
    }

    private fun newViewModel() = PostsViewModel(mapper, postRepository, dispatcherProvider)
}
