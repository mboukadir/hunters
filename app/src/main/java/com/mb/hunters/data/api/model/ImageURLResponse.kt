package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class ImageURLResponse(
    @Json(name = "44px")
    val the48Px: String? = null,
    @Json(name = "44px@2X")
    val the44Px2X: String? = null,
    @Json(name = "44px@3X")
    val the44Px3X: String? = null,
    val original: String? = null
)
