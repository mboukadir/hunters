package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class ThumbnailResponse(
    val id: String,
    @Json(name = "image_url")
    val imageURL: String,
)
