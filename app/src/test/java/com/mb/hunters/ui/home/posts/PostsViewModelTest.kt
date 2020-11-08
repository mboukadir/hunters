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
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import dagger.Lazy
import java.util.Calendar
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
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
    lateinit var postRepositoryLazy: Lazy<PostRepository>
    @Mock
    lateinit var postRepository: PostRepository
    @Mock
    lateinit var observer: Observer<List<PostUiModel>>

    private lateinit var postsViewModel: PostsViewModel

    @Before
    fun setup() {

        given(postRepositoryLazy.get()).willReturn(postRepository)
        postsViewModel = PostsViewModel(postMapper, postRepositoryLazy, dispatcherProvider)
    }

    @Test
    fun `Should load to day posts and return list of ui post`() = runBlockingTest {
        // GIVEN
        given(postRepository.loadPosts(0)).willReturn(POSTS)
        postsViewModel.toDayPosts.observeForever(observer)

        // WHEN
        postsViewModel.loadToDayPost()

        // THEN
        then(postRepository).should().loadPosts(0)
        then(observer).should().onChanged(POSTS_UI_MODELS)
    }

    @Test
    fun `Should load more posts and return list of ui post`() = runBlockingTest {

        // GIVEN
        given(postRepository.loadPosts(1)).willReturn(POSTS)
        postsViewModel.morePosts.observeForever(observer)

        // WHEN
        postsViewModel.loadMore(1)

        // THEN
        then(postRepository).should().loadPosts(1)
        then(observer).should().onChanged(POSTS_UI_MODELS)
    }

    @Test
    fun `Should refresh today post and return list of ui post`() = runBlockingTest {
        // GIVEN
        given(postRepository.refreshPosts(0)).willReturn(POSTS)
        postsViewModel.toDayPosts.observeForever(observer)

        // WHEN
        postsViewModel.refreshToDayPost()

        // THEN
        then(observer).should().onChanged(POSTS_UI_MODELS)
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
