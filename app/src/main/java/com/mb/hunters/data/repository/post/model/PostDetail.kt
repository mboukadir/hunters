package com.mb.hunters.data.repository.post.model

import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val name: String,
    val tagline: String,
    val redirectUrl: String,
    val votesCount: Long,
    val createdAt: LocalDateTime,
    val screenshotUrl: String,
    val thumbnailUrl: String,
    val medias: List<Media>,
    val description: String,
    val topics: List<String>,
    val badge: Badge?,
    val hunter: User?,
    val makers: List<User>
)

data class User(
    val id: Long,
    val name: String,
    val imageUrl: String
)

data class Badge(
    val date: LocalDateTime,
    val period: String,
    val position: Int
)

sealed class Media {
    data class Image(
        val id: Long,
        val priority: Long,
        val url: String
    ) : Media()

    data class Video(
        val id: Long,
        val priority: Long,
        val previewUrl: String,
        val code: String,
        val url: String
    ) : Media()

    data class Audio(
        val id: Long,
        val priority: Long,
        val url: String
    ) : Media()
}
