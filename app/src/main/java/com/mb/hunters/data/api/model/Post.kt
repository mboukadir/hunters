/*
 * Copyright 2017 Mohammed Boukadir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mb.hunters.data.api.model

import com.squareup.moshi.Json
import java.util.*

data class Post(
    val id: Long,
    val name: String,
    val tagline: String,
    @Json(name = "redirect_url") val redirectUrl: String,
    @Json(name = "votes_count") val votesCount: Long,
    @Json(name = "comments_count") val commentsCount: Long,
    val day: Date,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "screenshot_url") val screenshotUrl: ScreenshotUrl,
    val thumbnail: Thumbnail
)
