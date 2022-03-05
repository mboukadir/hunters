package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class MediaResponse(
    val id: Long,
    @Json(name = "kindle_asin")
    val kindleAsin: Any? = null,
    val priority: Long,
    val platform: String? = null,
    @Json(name = "video_id")
    val videoID: String? = null,
    @Json(name = "original_width")
    val originalWidth: Long,
    @Json(name = "original_height")
    val originalHeight: Long,
    @Json(name = "image_url")
    val imageURL: String,
    @Json(name = "media_type")
    val mediaType: MediaTypeResponse
)
