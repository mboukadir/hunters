package com.mb.hunters.ui.post.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class PosterItemState : Parcelable {
    @Parcelize
    data class Image(val url: String) : PosterItemState()
    @Parcelize
    data class Video(val previewUrl: String, val url: String) : PosterItemState()
}
