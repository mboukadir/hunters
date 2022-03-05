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

package com.mb.hunters.ui.post.state

data class DetailState(
    val getItUrl: String,
    val votesCount: Long,
    val featuredDate: String,
    val description: String,
    val topics: List<String>,
    val badge: BadgeState? = null,
    val hunter: Hunter? = null,
    val makers: List<Maker> = emptyList()
)

data class Hunter(
    val id: Long,
    val name: String,
    val imageUrl: String
)

data class Maker(
    val id: Long,
    val name: String,
    val imageUrl: String
)

data class BadgeState(
    val podiumPlace: PodiumPlace,
    val title: String,
    val date: String
)

sealed class PodiumPlace {
    abstract val displayablePosition: String

    object First : PodiumPlace() {
        override val displayablePosition: String = "1"
    }

    object Second : PodiumPlace() {
        override val displayablePosition: String = "2"
    }

    object Third : PodiumPlace() {
        override val displayablePosition: String = "3"
    }

    data class OffPodium(override val displayablePosition: String) : PodiumPlace()
}
