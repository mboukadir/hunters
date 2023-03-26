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

package com.mb.hunters.ui.post.detail

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.mb.hunters.R
import com.mb.hunters.ui.post.state.Hunter
import com.mb.hunters.ui.post.state.Maker
import com.mb.hunters.ui.post.state.PostDetailHeaderState
import com.mb.hunters.ui.post.state.PosterItemState
import com.mb.hunters.ui.post.state.PosterItemsState
import com.mb.hunters.ui.theme.HuntersTheme

@Composable
fun Header(
    postDetailHeaderState: PostDetailHeaderState,
    modifier: Modifier = Modifier,
    onPosterClick: (PosterItemsState) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = rememberImagePainter(postDetailHeaderState.imageUrl),
                contentDescription = stringResource(id = R.string.home_posts_item_thumbnail_discription),
                modifier = Modifier
                    .size(54.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            PostDetailTitles(
                title = postDetailHeaderState.title,
                subTitle = postDetailHeaderState.subTitle
            )
        }
        PostDetailPoster(
            posterItemsState = postDetailHeaderState.posterItemsState,
            onPosterClick = onPosterClick
        )
    }
}

@Composable
private fun PostDetailTitles(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    val typography = MaterialTheme.typography
    Column(modifier) {
        Text(
            text = title,
            style = typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = subTitle,
            style = typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailAppBar(
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    val backgroundColors = TopAppBarDefaults.smallTopAppBarColors()
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
        },
        actions = {},
        scrollBehavior = scrollBehavior,
        colors = backgroundColors,
    )
}

@Composable
private fun PostDetailPoster(
    posterItemsState: PosterItemsState,
    onPosterClick: (PosterItemsState) -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .wrapContentSize()
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(posterItemsState.items) { item ->
                PosterItem(posterItemState = item, onPosterItemClick = {
                    onPosterClick(posterItemsState)
                })
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        PosterItemsIndicator(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            imagesCount = posterItemsState.imageCount
        )
    }
}

@Composable
fun PosterItemsIndicator(
    imagesCount: Int,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = imagesCount > 0
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 4.dp))
                .border(
                    border = BorderStroke(2.dp, Color.White.copy(alpha = 0.7F)),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(2.dp)
                .drawBehind {
                    drawRect(color = Color.Black.copy(alpha = 0.2F))
                }
                .padding(horizontal = 8.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "",
                tint = Color.White
            )

            Text(
                text = "$imagesCount",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color.White
            )
        }
    }
}

@Composable
private fun LazyItemScope.PosterItem(
    posterItemState: PosterItemState,
    onPosterItemClick: (PosterItemState) -> Unit
) {
    when (posterItemState) {
        is PosterItemState.Image -> {
            Image(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .defaultMinSize(minWidth = 50.dp)
                    .clickable(onClick = {
                        onPosterItemClick(posterItemState)
                    }),
                painter = rememberImagePainter(
                    data = posterItemState.url
                ),
                contentDescription = ""
            )
        }

        is PosterItemState.Video ->

            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight()
                    .clickable(onClick = {
                        onPosterItemClick(posterItemState)
                    })
            ) {
                Image(
                    modifier = Modifier
                        .matchParentSize()
                        .drawWithContent {
                            drawContent()
                            drawRect(color = Color.Black.copy(alpha = 0.4F))
                        },
                    painter = rememberImagePainter(
                        data = posterItemState.previewUrl
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    modifier = Modifier
                        .size(54.dp)
                        .align(alignment = Alignment.Center),
                    imageVector = Icons.Outlined.PlayCircleOutline,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
    }
}

@Composable
fun Makers(
           hunter: Hunter?,
           makers: List<Maker>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        if (hunter != null) {
            item {
                val dividerColor =
                    MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
                MakerItem(
                    modifier = Modifier
                        .wrapContentSize()
                        .drawBehind {
                            drawLine(
                                color = dividerColor,
                                start = Offset(x = size.width, y = 0F),
                                end = Offset(x = size.width, y = size.height)
                            )
                        },
                    name = hunter.name,
                    imageUrl = hunter.imageUrl,
                    badgeColor = Color.Black,
                    badgeText = stringResource(id = R.string.detail_post_badge_text_hunter)
                )
            }
        }
        items(makers) { maker ->
            MakerItem(
                modifier = Modifier
                    .wrapContentSize(),
                name = maker.name,
                imageUrl = maker.imageUrl,
                badgeColor = Color.Green,
                badgeText = stringResource(id = R.string.detail_post_badge_text_maker)
            )
        }
    }
}

@Composable
private fun MakerItem(
    name: String,
    imageUrl: String,
    badgeColor: Color,
    badgeText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(32.dp),
                painter = rememberImagePainter(imageUrl), contentDescription = ""
            )

            Box(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .clip(shape = CircleShape)
                    .background(Color.White)
                    .padding(1.dp)
                    .clip(shape = CircleShape)
                    .size(12.dp)
                    .background(badgeColor)
                    .align(alignment = Alignment.TopEnd)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = badgeText,
                    fontSize = 6.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun MakersDarkPreview() {
    MakersPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun MakersLightPreview() {
    MakersPreview()
}

@Composable
@SuppressLint("ComposeModifierMissing")
fun MakersPreview() {

    val hunter = Hunter(
        id = 1,
        name = "Rodrigo Mendoza-Smith",
        imageUrl = "https://ph-avatars.imgix.net/3054112/original?auto=format&fit=crop&crop=faces&w=48&h=48",
    )
    val makers = listOf(
        Maker(
            id = 1,
            name = "Rodrigo Mendoza-Smith",
            imageUrl = "https://ph-avatars.imgix.net/3054112/original?auto=format&fit=crop&crop=faces&w=48&h=48",
        )
    )

    HuntersTheme {

        Box {
            Makers(
                modifier = Modifier
                    .wrapContentSize(),
                hunter = hunter,
                makers = makers,
            )
        }
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun PostDetailPosterDarkPreview() {
    PostDetailPosterPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun PostDetailPosterLightPreview() {
    PostDetailPosterPreview()
}

@Composable
private fun PostDetailPosterPreview() {
    val posterItemsState = listOf<PosterItemState>()
    HuntersTheme {
        PosterItemsIndicator(
            modifier = Modifier.wrapContentSize(),
            imagesCount = 7,
        )
    }
}
