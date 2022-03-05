/*
 * Copyright 2022 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
