package com.mb.hunters.ui.post.state

import com.mb.hunters.R
import com.mb.hunters.data.repository.post.model.Media
import com.mb.hunters.data.repository.post.model.PostDetail
import com.mb.hunters.ui.base.Mapper
import com.mb.hunters.ui.common.StringResourcesProvider
import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PostDetailContentStateMapper @Inject constructor(
    private val stringResProvider: StringResourcesProvider,
    private val clock: Clock
) :
    Mapper<PostDetail, PostDetailContentState> {
    override fun map(type: PostDetail): PostDetailContentState {
        val posterItems = type.medias.toPosterItems()
        return PostDetailContentState(
            id = type.id,
            headerState = PostDetailHeaderState(
                title = type.name,
                subTitle = type.tagline,
                imageUrl = type.thumbnailUrl,
                posterItemsState = PosterItemsState(
                    items = posterItems,
                    imageCount = posterItems.filterIsInstance<PosterItemState.Image>().size
                )
            ),
            detailState = DetailState(
                getItUrl = type.redirectUrl,
                votesCount = type.votesCount,
                featuredDate = type.createdAt.featuredDate(),
                description = type.description,
                topics = type.topics.map { it.uppercase() },
                badge = type.badge?.let {
                    BadgeState(
                        podiumPlace = it.position.toPodiumPlace(),
                        title = stringResProvider.getString(
                            R.string.detail_post_badge_title,
                            it.position
                        ),
                        date = it.date.badgeDisplayableDate()
                    )
                },
                hunter = type.hunter?.let {
                    Hunter(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl
                    )
                },
                makers = type.makers.map {
                    Maker(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl
                    )
                }
            )
        )
    }

    private fun Int.toPodiumPlace(): PodiumPlace {
        return when (this) {
            1 -> PodiumPlace.First
            2 -> PodiumPlace.Second
            3 -> PodiumPlace.Third
            else -> {
                PodiumPlace.OffPodium("$this")
            }
        }
    }

    private fun List<Media>.toPosterItems(): List<PosterItemState> {
        return this.map {
            when (it) {
                is Media.Audio -> {
                    PosterItemState.Image(url = it.url)
                }
                is Media.Image -> {
                    PosterItemState.Image(url = it.url)
                }
                is Media.Video -> PosterItemState.Video(previewUrl = it.previewUrl, url = it.url)
            }
        }
    }

    private fun LocalDateTime.featuredDate(): String {
        val period = Period.between(this.toLocalDate(), LocalDateTime.now(clock).toLocalDate())
        return if (period.years > 0) {
            stringResProvider.getQuantityString(
                R.plurals.detail_post_featured_year_ago,
                period.years,
                period.years
            )
        } else if (period.months > 0) {
            stringResProvider.getQuantityString(
                R.plurals.detail_post_featured_month_ago,
                period.months,
                period.months
            )
        } else if (period.days > 0) {
            stringResProvider.getQuantityString(
                R.plurals.detail_post_featured_day_ago,
                period.days,
                period.days
            )
        } else {
            val hoursAgo = Duration.between(this, LocalDateTime.now(clock)).toHours().toInt().let {
                if (it == 0) {
                    1
                } else {
                    it
                }
            }
            stringResProvider.getQuantityString(
                resId = R.plurals.detail_post_featured_hour_ago,
                quantity = hoursAgo,
                hoursAgo
            )
        }
    }

    private fun LocalDateTime.badgeDisplayableDate(): String {
        return if (isToday()) {
            stringResProvider.getString(R.string.detail_post_badge_date_today)
        } else {
            this.format(DateTimeFormatter.ofPattern("MMMM d, y", Locale.US))
        }
    }

    private fun LocalDateTime.isToday(): Boolean {
        val currentDateTime = LocalDateTime.now(clock)
        return this.dayOfYear == currentDateTime.dayOfYear
    }
}
