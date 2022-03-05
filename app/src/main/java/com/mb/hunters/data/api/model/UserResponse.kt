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
import java.util.Date

data class UserResponse(
    val id: Long,
    @Json(name = "created_at")
    val createdAt: Date,
    val name: String,
    val username: String,
    val headline: String? = null,
    @Json(name = "twitter_username")
    val twitterUsername: String? = null,
    @Json(name = "website_url")
    val websiteURL: String? = null,
    @Json(name = "profile_url")
    val profileURL: String,
    @Json(name = "image_url")
    val imageURL: ImageURLResponse
)
