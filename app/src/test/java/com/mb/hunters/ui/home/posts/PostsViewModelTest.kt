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
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.data.repository.post.PostRepository
import com.mb.hunters.test.TestDispatcherProvider.dispatcherProvider
import com.mb.hunters.test.TestSchedulerProvider
import dagger.Lazy
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Calendar
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var postRepositoryLazy: Lazy<PostRepository>
    @Mock lateinit var postRepository: PostRepository
    @Mock lateinit var observer: Observer<List<PostUiModel>>

    @Captor private lateinit var daysAgo: ArgumentCaptor<Long>
    @Captor private lateinit var postsUiModels: ArgumentCaptor<List<PostUiModel>>

    private lateinit var postsViewModel: PostsViewModel

    @Before
    fun setup() {

        `when`(postRepositoryLazy.get()).thenReturn(postRepository)
        postsViewModel = PostsViewModel(postMapper, TestSchedulerProvider, dispatcherProvider, postRepositoryLazy)
    }

    @Test
    fun shouldLoadToDayPostReturnListOfUIPostModel() {

        `when`(postRepository.loadPosts(ArgumentMatchers.anyLong())).thenReturn(Single.just(POSTS))
        postsViewModel.toDayPosts.observeForever(observer)

        postsViewModel.loadToDayPost()

        verify<PostRepository>(postRepository).loadPosts(daysAgo.capture())
        assertEquals(0, daysAgo.value)

        verify(observer).onChanged(postsUiModels.capture())
        assertEquals(POSTS_UI_MODELS, postsUiModels.value)
    }

    @Test
    fun shouldLoadDayPostReturnError() {

        `when`(postRepository.loadPosts(ArgumentMatchers.anyLong())).thenReturn(
                Single.error(Exception()))
        postsViewModel.toDayPosts.observeForever(observer)

        postsViewModel.loadToDayPost()

        verify(postRepository).loadPosts(daysAgo.capture())
        assertEquals(0, daysAgo.value)

        verify(observer, never()).onChanged(ArgumentMatchers.any())
    }

    @Test
    fun shouldLoadMoreReturnListOfUiPostModel() {

        `when`(postRepository.loadPosts(ArgumentMatchers.anyLong())).thenReturn(Single.just(POSTS))
        postsViewModel.morePosts.observeForever(observer)

        postsViewModel.loadMore(1)

        verify(postRepository).loadPosts(daysAgo.capture())
        assertEquals(1, daysAgo.value)

        verify(observer).onChanged(POSTS_UI_MODELS)
    }

    @Test
    fun shouldLoadMoreReturnException() {

        `when`(postRepository.loadPosts(ArgumentMatchers.anyLong())).thenReturn(
                Single.error(Exception()))
        postsViewModel.morePosts.observeForever(observer)

        postsViewModel.loadMore(2)

        verify(postRepository).loadPosts(daysAgo.capture())
        assertEquals(2, daysAgo.value)

        verify(observer, never()).onChanged(ArgumentMatchers.any())
    }

    @Test
    fun shouldRefreshToDayPostReturnListOfUiPostModel() {

        `when`(postRepository.refreshPosts(ArgumentMatchers.anyLong())).thenReturn(
                Single.just(POSTS))
        postsViewModel.toDayPosts.observeForever(observer)

        postsViewModel.refreshToDayPost()

        verify(postRepository).refreshPosts(daysAgo.capture())
        assertEquals(0, daysAgo.value)

        verify(observer).onChanged(POSTS_UI_MODELS)
    }

    @Test
    fun shouldRefreshToDayPostReturnException() {

        `when`(postRepository.refreshPosts(ArgumentMatchers.anyLong())).thenReturn(
                Single.error(Exception()))
        postsViewModel.toDayPosts.observeForever(observer)

        postsViewModel.refreshToDayPost()
        verify(postRepository).refreshPosts(daysAgo.capture())

        assertEquals(0, daysAgo.value)

        verify(observer, never()).onChanged(ArgumentMatchers.any())
    }

    companion object {

        val postMapper = PostMapper()

        private val TODAY = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time

        private val POST = PostEntity(
                id = 0,
                name = "Name",
                tagline = "TagLine",
                redirectUrl = "redirectUrl",
                votesCount = 1,
                commentsCount = 1,
                day = TODAY,
                createdAt = TODAY,
                thumbnailUrl = "thumbnailUrl",
                screenshotUrl = "screenshotUrl"
        )

        private val POSTS = listOf(
                POST,
                POST.copy(id = 2)
        )

        private val POSTS_UI_MODELS = POSTS.map {

            postMapper.mapToUiModel(it)
        }
    }
}