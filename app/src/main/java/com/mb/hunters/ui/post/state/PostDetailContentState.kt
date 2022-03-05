package com.mb.hunters.ui.post.state

data class PostDetailContentState(
    val id: Long,
    val headerState: PostDetailHeaderState,
    val detailState: DetailState
)
