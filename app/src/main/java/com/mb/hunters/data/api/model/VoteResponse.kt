package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class VoteResponse(
    val id: Long,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "user_id")
    val userID: Long,

    @Json(name = "post_id")
    val postID: Long,

    val user: UserResponse
)
