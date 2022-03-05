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

package com.mb.hunters.ui.post.poster

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.mb.hunters.R
import com.mb.hunters.ui.common.ErrorScreen
import com.mb.hunters.ui.post.state.PosterItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PostPosterScreen(
    navController: NavController,
    items: List<PosterItemState>?,
    modifier: Modifier = Modifier
) {
    val images = items?.filterIsInstance<PosterItemState.Image>()

    if (images != null) {
        PosterPager(
            images = images, onCloseClick = {
                navController.navigateUp()
            }, modifier = modifier
        )
    } else {
        ErrorScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PosterPager(
    images: List<PosterItemState.Image>,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            count = images.size,
            state = pagerState
        ) { page ->
            PosterPage(images[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp),
        )

        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp)
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .size(size = 32.dp),
            onClick = onCloseClick
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PosterPage(
    image: PosterItemState.Image
) {
    Box(modifier = Modifier.fillMaxSize()) {
        var pagerColorState by remember(image.url) { mutableStateOf(Color.Transparent) }
        val painter = rememberImagePainter(data = image.url, builder = {
            allowHardware(false)
        })

        val drawable = (painter.state as? ImagePainter.State.Success)?.result?.drawable
        if (drawable != null) {
            LaunchedEffect(image.url) {
                withContext(Dispatchers.Default) {
                    val vibrantColor = Palette.Builder(drawable.toBitmap()).generate()
                        .getDominantColor(Color.Transparent.toArgb())
                    pagerColorState = Color(vibrantColor)
                }
            }
        }
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(pagerColorState),
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Fit
        )
        if (painter.state !is ImagePainter.State.Success) {
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(50.dp),
                painter = painterResource(id = R.drawable.ic_place_hoder_24),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun PostPosterScreenDarkPreview() {
    PostPosterScreenPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun PostPosterScreenLightPreview() {
    PostPosterScreenPreview()
}

@Composable
fun PostPosterScreenPreview() {
    val images = listOf(
        PosterItemState.Image(""),
        PosterItemState.Image(""),
        PosterItemState.Image("")
    )
    PosterPager(images = images, onCloseClick = { /*TODO*/ })
}
