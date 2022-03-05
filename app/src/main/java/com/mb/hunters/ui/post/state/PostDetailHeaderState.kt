package com.mb.hunters.ui.post.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PostDetailHeaderState(
    val title: String,
    val subTitle: String,
    val imageUrl: String,
    val posterItemsState: PosterItemsState = PosterItemsState(),
)

@Parcelize
data class PosterItemsState(
    val items: List<PosterItemState> = emptyList(),
    val imageCount: Int = 0
) : Parcelable
