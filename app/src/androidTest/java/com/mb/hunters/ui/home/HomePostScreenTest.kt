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

package com.mb.hunters.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.recyclerview.widget.RecyclerView
import com.mb.hunters.R
import com.mb.hunters.R.id
import com.mb.hunters.TestApplication
import com.mb.hunters.data.database.entity.PostEntity
import com.mb.hunters.test.RecyclerViewMatcher
import com.mb.hunters.ui.home.posts.PostMapper
import com.mb.hunters.ui.home.posts.PostUiModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import java.util.Calendar

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomePostScreenTest {

    @get:Rule
    @JvmField
    val activity = ActivityTestRule(HomeActivity::class.java, false, false)

    @Before
    fun setup() {

        `when`(TestApplication.appComponent().postRepository().loadPosts(
                AdditionalMatchers.not(ArgumentMatchers.eq(0L))))
                .thenReturn(Single.just<List<PostEntity>>(emptyList()))
    }

    @Ignore
    @Test
    fun activityLaunches() {

        `when`(TestApplication.appComponent().postRepository().loadPosts(0))
                .thenReturn(Single.just<List<PostEntity>>(listOf(POST)))

        activity.launchActivity(null)
    }

    @Ignore
    @Test
    fun postsDisplay() {
        `when`(TestApplication.appComponent().postRepository().loadPosts(0))
                .thenReturn(Single.just<List<PostEntity>>(listOf(POST)))

        activity.launchActivity(null)

        checkPostItemDisplay(POST_UIMODEL, 0)
    }

    @Ignore
    @Test
    fun postsAreScrollable() {

        `when`(TestApplication.appComponent().postRepository().loadPosts(0))
                .thenReturn(Single.just<List<PostEntity>>(POSTS))

        activity.launchActivity(null)

        POSTS_UIMODEL.forEachIndexed { index, postUiModel ->
            onView(withId(R.id.postRecyclerView))
                    .perform(RecyclerViewActions.scrollToPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(index))
            checkPostItemDisplay(postUiModel, index)
        }
    }

    private fun checkPostItemDisplay(postUiModel: PostUiModel, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(id.postRecyclerView).atPosition(position))
                .check(selectedDescendantsMatch(withId(id.screenShot),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.thumbnail),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.commentsIcon),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.upVotesIcon),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.name),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.tagline),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.commentsCount),
                        withEffectiveVisibility(Visibility.VISIBLE)))
                .check(selectedDescendantsMatch(withId(id.upVotesCount),
                        withEffectiveVisibility(Visibility.VISIBLE)))

                .check(selectedDescendantsMatch(withId(id.name), withText(postUiModel.title)))
                .check(selectedDescendantsMatch(withId(id.tagline), withText(postUiModel.subTitle)))
                .check(selectedDescendantsMatch(withId(id.commentsCount),
                        withText(postUiModel.commentsCount.toString())))
                .check(selectedDescendantsMatch(withId(id.upVotesCount),
                        withText(postUiModel.votesCount.toString())))
    }

    companion object {

        private val postMapper = PostMapper()

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

        private val POST_UIMODEL = postMapper.mapToUiModel(POST)

        private val POSTS = listOf(
                POST,
                POST.copy(id = 2, name = POST.name + "2", commentsCount = 2, votesCount = 4),
                POST.copy(id = 3, name = POST.name + "3", commentsCount = 3, votesCount = 6),
                POST.copy(id = 4, name = POST.name + "4", commentsCount = 4, votesCount = 8),
                POST.copy(id = 5, name = POST.name + "5", commentsCount = 5, votesCount = 10),
                POST.copy(id = 6, name = POST.name + "6", commentsCount = 6, votesCount = 12),
                POST.copy(id = 7, name = POST.name + "7", commentsCount = 7, votesCount = 14),
                POST.copy(id = 8, name = POST.name + "8", commentsCount = 8, votesCount = 16)
        )

        private val POSTS_UIMODEL = POSTS.map { postMapper.mapToUiModel(it) }
    }
}