package com.mb.hunters.data.api.model

import com.squareup.moshi.Json
import java.util.Date

data class UserResponse(
    val id: Long,
    @Json(name = "created_at")
    val createdAt: Date,
    val name: String,
    val username: String,
    val headline: String? = null,
    @Json(name = "twitter_username")
    val twitterUsername: String? = null,
    @Json(name = "website_url")
    val websiteURL: String? = null,
    @Json(name = "profile_url")
    val profileURL: String,
    @Json(name = "image_url")
    val imageURL: ImageURLResponse
)
