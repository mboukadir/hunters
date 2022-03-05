package com.mb.hunters.data.api.model

import com.squareup.moshi.Json

data class CommentResponse(
    val id: Long,
    val body: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "parent_comment_id")
    val parentCommentID: Any? = null,
    @Json(name = "user_id")
    val userID: Long,
    @Json(name = "subject_id")
    val subjectID: Long,
    @Json(name = "child_comments_count")
    val childCommentsCount: Long,
    val url: String,
    @Json(name = "post_id")
    val postID: Long,
    @Json(name = "subject_type")
    val subjectType: String,
    val sticky: Boolean,
    val votes: Long,
    val user: UserResponse,
    @Json(name = "child_comments")
    val childComments: List<Any?>,
    val maker: Boolean,
    val hunter: Boolean,
    @Json(name = "live_guest")
    val liveGuest: Boolean
)
