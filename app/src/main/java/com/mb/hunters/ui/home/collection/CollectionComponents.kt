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

package com.mb.hunters.ui.home.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mb.hunters.R
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import com.mb.hunters.ui.theme.ThemedPreview
import com.mb.hunters.ui.theme.backgroundTransparent

@Composable
fun CollectionItem(
    collectionUiModel: CollectionUiModel,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    elevation: Dp = 8.dp,
    onClick: () -> Unit = {}
) {
    Surface(
        shape = shape,
        modifier = modifier
            .padding(4.dp)
            .height(
                200.dp
            )
            .clickable {
                onClick()
            },
        tonalElevation = elevation
    ) {

        Image(
            painter = rememberImagePainter(collectionUiModel.backgroundImageUrl),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentDescription = stringResource(id = R.string.empty),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier.fillMaxHeight()
                .fillMaxWidth().background(color = backgroundTransparent),
        ) {

            Text(
                text = collectionUiModel.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight()
            )
        }
    }
}

@Preview
@Composable
fun PostItemPreview() {
    val id = 1L
    val collectionUiModel = CollectionUiModel(
        id = id,
        name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam varius",
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque eu pellentesque ante. Ut consectetur, turpis eget rhoncus feugiat, metus leo faucibus magna, quis scelerisque nulla lacus vitae metus. Nullam metus lorem, venenatis eget quam sed, auctor aliquam leo. Morbi maximus pulvinar leo vitae fringilla. Sed venenatis at neque in fringilla. Duis facilisis erat ut ligula interdum, sed mattis nunc facilisis. Donec auctor fermentum mollis. In hac habitasse platea dictumst. Aliquam et metus vel nunc suscipit mollis. Pellentesque ornare augue sit amet egestas consectetur. Praesent congue et justo in posuere. Etiam malesuada lorem eget nisi eleifend aliquam. Curabitur vel quam eget orci dictum aliquam.",
        backgroundImageUrl = "Subtitle$id",
        collectionUrl = "url"
    )

    ThemedPreview {
        CollectionItem(collectionUiModel = collectionUiModel)
    }
}
