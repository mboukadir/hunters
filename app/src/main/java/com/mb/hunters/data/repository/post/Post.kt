package com.mb.hunters.data.repository.post

import java.util.Date

data class Post(
    val id: Long,
    val name: String,
    val tagline: String,
    val redirectUrl: String,
    val votesCount: Long,
    val commentsCount: Long,
    val day: Date,
    val createdAt: Date,
    val screenshotUrl: String,
    val thumbnailUrl: String
)
