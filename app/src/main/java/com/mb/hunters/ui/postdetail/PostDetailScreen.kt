package com.mb.hunters.ui.postdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostDetailScreen(postId: Long, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = "PostDetail $postId")
    }
}
