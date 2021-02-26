package com.mb.hunters.ui.home.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.sp
import com.mb.hunters.R
import com.mb.hunters.ui.home.collection.model.CollectionUiModel
import com.mb.hunters.ui.theme.ThemedPreview
import com.mb.hunters.ui.theme.backgroundTransparent
import dev.chrisbanes.accompanist.coil.CoilImage

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
        elevation = elevation
    ) {

        CoilImage(
            data = collectionUiModel.backgroundImageUrl,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentDescription = stringResource(id = R.string.empty),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier.fillMaxHeight()
                .fillMaxWidth().background(color = backgroundTransparent),
        ) {

            Text(
                text = collectionUiModel.name,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White,
                    fontSize = 16.sp
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
