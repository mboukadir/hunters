package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

enum class MediaTypeResponse {
    @Json(name = "image")
    IMAGE,

    @Json(name = "video")
    VIDEO,

    @Json(name = "audio")
    AUDIO,
}
