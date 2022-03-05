package com.mb.hunters.data.api.model

import java.util.Date

data class BadgeResponse(
    val type: String,
    val data: InfoBadge
)

data class InfoBadge(val position: Int, val date: Date, val period: String)
