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

package com.mb.hunters.ui.post.state

import com.google.common.truth.Truth.assertThat
import com.mb.hunters.R
import com.mb.hunters.data.repository.post.model.Badge
import com.mb.hunters.data.repository.post.model.Media
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.data.repository.post.model.User
import com.mb.hunters.ui.common.StringResourcesProvider
import com.nhaarman.mockitokotlin2.given
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostDetailContentStateMapperTest {

    @Mock
    lateinit var stringResourcesProvider: StringResourcesProvider

    @Test
    fun `Should return post detail content state featured now`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 HOUR AGO")

        val subject = newSubject()
        // WHEN

        val actual = subject.map(POST_Detail)

        // THEN

        assertThat(actual).isEqualTo(EXPECTED)
    }

    @Test
    fun `Should return post detail content state featured 2 hour ago`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = 2,
                2
            )
        ).willReturn("FEATURED 2 HOURS AGO")

        val clock = Clock.offset(FIXED_CLOCK, Duration.ofHours(2))
        val subject = newSubject(clock = clock)
        val expected =
            EXPECTED.copy(detailState = EXPECTED.detailState.copy(featuredDate = "FEATURED 2 HOURS AGO"))
        // WHEN

        val actual = subject.map(POST_Detail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state featured 1 day ago`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_day_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 DAY AGO")

        val clock = Clock.offset(FIXED_CLOCK, Duration.ofHours(26))
        val subject = newSubject(clock = clock)
        val expected =
            EXPECTED.copy(detailState = EXPECTED.detailState.copy(featuredDate = "FEATURED 1 DAY AGO"))

        // WHEN

        val actual = subject.map(POST_Detail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state featured 1 month ago`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_month_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 Month AGO")

        val clock = Clock.offset(FIXED_CLOCK, Duration.ofDays(36))
        val subject = newSubject(clock = clock)
        val expected =
            EXPECTED.copy(detailState = EXPECTED.detailState.copy(featuredDate = "FEATURED 1 Month AGO"))

        // WHEN

        val actual = subject.map(POST_Detail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state featured 1 year ago`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_year_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 YEAR AGO")

        val clock = Clock.offset(FIXED_CLOCK, Duration.ofDays(380))
        val subject = newSubject(clock = clock)
        val expected =
            EXPECTED.copy(detailState = EXPECTED.detailState.copy(featuredDate = "FEATURED 1 YEAR AGO"))

        // WHEN

        val actual = subject.map(POST_Detail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state as 1 product of the day`() {
        // GIVEN
        val position = 1
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 HOUR AGO")

        given(stringResourcesProvider.getString(R.string.detail_post_badge_date_today)).willReturn("TODAY")

        given(
            stringResourcesProvider.getString(
                R.string.detail_post_badge_title,
                position
            )
        ).willReturn("#$position Product of the Day")
        val postDetail = POST_Detail.copy(
            badge = Badge(
                date = LocalDateTime.now(FIXED_CLOCK),
                position = position,
                period = ""
            )
        )

        val expected = EXPECTED.copy(
            detailState = EXPECTED.detailState.copy(
                badge = BadgeState(
                    podiumPlace = PodiumPlace.First,
                    date = "TODAY",
                    title = "#$position Product of the Day"
                )
            )
        )
        val subject = newSubject()
        // WHEN

        val actual = subject.map(postDetail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state as 2 product of the day`() {
        // GIVEN
        val position = 2
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 HOUR AGO")

        given(stringResourcesProvider.getString(R.string.detail_post_badge_date_today)).willReturn("TODAY")

        given(
            stringResourcesProvider.getString(
                R.string.detail_post_badge_title,
                position
            )
        ).willReturn("#$position Product of the Day")
        val postDetail = POST_Detail.copy(
            badge = Badge(
                date = LocalDateTime.now(FIXED_CLOCK),
                position = position,
                period = ""
            )
        )

        val expected = EXPECTED.copy(
            detailState = EXPECTED.detailState.copy(
                badge = BadgeState(
                    podiumPlace = PodiumPlace.Second,
                    date = "TODAY",
                    title = "#$position Product of the Day"
                )
            )
        )
        val subject = newSubject()
        // WHEN

        val actual = subject.map(postDetail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state as 3 product of the day`() {
        // GIVEN
        val position = 3
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = 1,
                1
            )
        ).willReturn("FEATURED 1 HOUR AGO")

        given(stringResourcesProvider.getString(R.string.detail_post_badge_date_today)).willReturn("TODAY")

        given(
            stringResourcesProvider.getString(
                R.string.detail_post_badge_title,
                position
            )
        ).willReturn("#$position Product of the Day")
        val postDetail = POST_Detail.copy(
            badge = Badge(
                date = LocalDateTime.now(FIXED_CLOCK),
                position = position,
                period = ""
            )
        )

        val expected = EXPECTED.copy(
            detailState = EXPECTED.detailState.copy(
                badge = BadgeState(
                    podiumPlace = PodiumPlace.Third,
                    date = "TODAY",
                    title = "#$position Product of the Day"
                )
            )
        )
        val subject = newSubject()
        // WHEN

        val actual = subject.map(postDetail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Should return post detail content state as first product of the yesterday`() {
        // GIVEN
        given(
            stringResourcesProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_day_ago,
                quantity = 1,
                1

            )
        ).willReturn("FEATURED 1 DAY AGO")

        given(
            stringResourcesProvider.getString(
                R.string.detail_post_badge_title,
                1
            )
        ).willReturn("#1 Product of the Day")

        val yesterdayClock = Clock.offset(FIXED_CLOCK, Duration.ofDays(-1))

        val postDetail = POST_Detail.copy(
            createdAt = LocalDateTime.now(yesterdayClock),
            badge = Badge(
                date = LocalDateTime.now(yesterdayClock),
                position = 1,
                period = ""
            )
        )

        val expected = EXPECTED.copy(
            detailState = EXPECTED.detailState.copy(
                featuredDate = "FEATURED 1 DAY AGO",
                badge = BadgeState(
                    podiumPlace = PodiumPlace.First,
                    date = "March 4, 2022",
                    title = "#1 Product of the Day"
                )
            )
        )
        val subject = newSubject()
        // WHEN

        val actual = subject.map(postDetail)

        // THEN

        assertThat(actual).isEqualTo(expected)
    }

    private fun newSubject(clock: Clock = FIXED_CLOCK) = PostDetailContentStateMapper(
        stringResProvider = stringResourcesProvider,
        clock = clock
    )

    companion object {
        private const val INSTANT_EXPECTED = "2022-03-05T10:00:30Z"
        private val FIXED_CLOCK = Clock.fixed(Instant.parse(INSTANT_EXPECTED), ZoneId.of("UTC"))

        private val POST_Detail = PostDetail(
            id = 1,
            name = "name",
            tagline = "tagline",
            redirectUrl = "redirectURL",
            votesCount = 5,
            createdAt = LocalDateTime.now(FIXED_CLOCK),
            screenshotUrl = "smallImgUrl",
            thumbnailUrl = "imageUR",
            medias = listOf(
                Media.Video(
                    id = 1,
                    priority = 1,
                    previewUrl = "imageURL",
                    code = "",
                    url = "Video imageURL"
                ),
                Media.Image(
                    id = 2,
                    priority = 2,
                    url = "Image imageURL"
                ),
                Media.Audio(
                    id = 3,
                    priority = 3,
                    url = "Audio imageURL"
                )
            ),
            description = "description",
            topics = listOf("topic name"),
            badge = null,
            hunter = null,
            makers = listOf(
                User(
                    id = 2,
                    name = "maker",
                    imageUrl = "Image url"
                )
            )
        )

        val EXPECTED = PostDetailContentState(
            id = POST_Detail.id,
            headerState = PostDetailHeaderState(
                title = POST_Detail.name,
                subTitle = POST_Detail.tagline,
                imageUrl = POST_Detail.thumbnailUrl,
                posterItemsState = PosterItemsState(
                    items = listOf(
                        PosterItemState.Video(previewUrl = "imageURL", url = "Video imageURL"),
                        PosterItemState.Image(url = "Image imageURL"),
                        PosterItemState.Image(url = "Audio imageURL"),
                    ),
                    imageCount = 2
                )
            ),
            detailState = DetailState(
                getItUrl = "redirectURL",
                votesCount = 5,
                featuredDate = "FEATURED 1 HOUR AGO",
                topics = listOf("TOPIC NAME"),
                description = "description",
                hunter = null,
                makers = listOf(
                    Maker(
                        id = 2,
                        name = "maker",
                        imageUrl = "Image url"
                    )
                )
            )
        )
    }
}
