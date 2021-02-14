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

package com.mb.hunters.ui.home.posts.model

data class PostUiModel(
    val id: Long,
    val title: String,
    val subTitle: String,
    val postUrl: String,
    val votesCount: Long,
    val commentsCount: Long,
    val daysAgo: Long,
    val date: String,
    val bigImageUrl: String,
    val smallImageUrl: String
)
