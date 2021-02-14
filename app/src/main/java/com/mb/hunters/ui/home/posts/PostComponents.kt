package com.mb.hunters.ui.home.posts

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mb.hunters.R
import com.mb.hunters.ui.home.posts.model.PostUiModel
import com.mb.hunters.ui.theme.ThemedPreview
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PostItem(
    postUiModel: PostUiModel,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.padding(16.dp)) {

        CoilImage(
            data = postUiModel.bigImageUrl,
            contentDescription = stringResource(id = R.string.home_post_item_img_description),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
                    shape = MaterialTheme.shapes.medium
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier) {
            CoilImage(
                data = postUiModel.smallImageUrl,
                contentDescription = stringResource(id = R.string.home_posts_item_thumbnail_discription),
                modifier = Modifier
                    .size(46.dp)
                    .clip(shape = CircleShape)
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
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
            tint = MaterialTheme.colors.secondary
        )

        Text(text = counter, color = MaterialTheme.colors.secondary)
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
            style = typography.h6,
        )

        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = subTitle,
                style = typography.body2
            )
        }
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
