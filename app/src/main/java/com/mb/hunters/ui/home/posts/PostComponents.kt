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

package com.mb.hunters.ui.home.posts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mb.hunters.R
import com.mb.hunters.ui.home.posts.model.PostUiModel
import com.mb.hunters.ui.theme.ThemedPreview

@Composable
fun PostItem(
    postUiModel: PostUiModel,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.padding(16.dp)) {

        val imageShape = RoundedCornerShape(4.dp)
        Image(
            painter = rememberImagePainter(postUiModel.bigImageUrl),
            contentDescription = stringResource(id = R.string.home_post_item_img_description),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = imageShape)
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    shape = imageShape
                )
                .padding(2.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier) {
            Image(
                painter = rememberImagePainter(postUiModel.smallImageUrl),
                contentDescription = stringResource(id = R.string.home_posts_item_thumbnail_discription),
                modifier = Modifier
                    .size(46.dp)
                    .clip(shape = CircleShape)
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            PostTitles(
                title = postUiModel.title,
                subTitle = postUiModel.subTitle,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.padding(start = 62.dp)) {
            Counter(
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.ic_comment_black_24dp),
                counter = postUiModel.commentsCount.toString(),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(align = Alignment.Start)
            )

            Counter(
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.ic_keyboard_arrow_up_black_24dp),
                counter = postUiModel.votesCount.toString(),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(align = Alignment.End)

            )
        }
    }
}

@Composable
fun Counter(
    onClick: () -> Unit,
    painter: Painter,
    counter: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )

        Text(text = counter, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun PostTitles(
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

@Preview
@Composable
fun PostItemPreview() {
    val id = 1L
    val postUiModel = PostUiModel(
        id = id,
        title = "title $id title title title",
        subTitle = "Subtitle$id",
        postUrl = "url",
        votesCount = id,
        commentsCount = id,
        daysAgo = id,
        date = "Date $id",
        bigImageUrl = "imageUrl",
        smallImageUrl = "smallImageUrl"
    )
    ThemedPreview {
        PostItem(postUiModel = postUiModel)
    }
}
