package com.mb.hunters.data.api.model

import com.squareup.moshi.Json
import java.util.Date

data class PostDetailResponse(
    val id: Long,
    val name: String,
    val tagline: String,
    val slug: String,
    @Json(name = "votes_count")
    val votesCount: Long,
    @Json(name = "created_at")
    val createdAt: Date,
    @Json(name = "maker_inside")
    val makerInside: Boolean,
    val makers: List<UserResponse>,
    @Json(name = "redirect_url")
    val redirectURL: String,
    @Json(name = "screenshot_url")
    val screenshotURL: ScreenshotUrl,
    val thumbnail: ThumbnailResponse,
    val topics: List<TopicResponse>,
    val user: UserResponse,
    @Json(name = "reviews_count")
    val reviewsCount: Long,
    val badges: List<BadgeResponse>,
    val comments: List<CommentResponse>,
    val votes: List<VoteResponse>,
    val media: List<MediaResponse>,
    val description: String
)

data class PostDetailResponseEnvelope(
    val post: PostDetailResponse
)
