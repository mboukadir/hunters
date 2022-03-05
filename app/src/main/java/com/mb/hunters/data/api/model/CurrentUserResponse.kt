package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class CurrentUserResponse(
    @Json(name = "voted_for_post")
    val votedForPost: Boolean,

    @Json(name = "commented_on_post")
    val commentedOnPost: Boolean
)
