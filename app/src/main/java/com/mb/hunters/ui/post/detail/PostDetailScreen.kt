package com.mb.hunters.ui.post.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme as MaterialTheme2
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding
import com.mb.hunters.ui.Screen
import com.mb.hunters.ui.common.ErrorScreen
import com.mb.hunters.ui.post.detail.PostDetailViewModel.PostDetailUiState
import com.mb.hunters.ui.post.state.BadgeState
import com.mb.hunters.ui.post.state.DetailState
import com.mb.hunters.ui.post.state.Hunter
import com.mb.hunters.ui.post.state.Maker
import com.mb.hunters.ui.post.state.PodiumPlace
import com.mb.hunters.ui.post.state.PostDetailContentState
import com.mb.hunters.ui.post.state.PostDetailHeaderState
import com.mb.hunters.ui.post.state.PosterItemsState
import com.mb.hunters.ui.theme.Bronze
import com.mb.hunters.ui.theme.Gold
import com.mb.hunters.ui.theme.HuntersTheme
import com.mb.hunters.ui.theme.Silver

@Composable
fun PostDetailScreen(
    navController: NavController,
    postDetailViewModel: PostDetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by postDetailViewModel.postDetailUiState.collectAsState()
    PostDetailScreen(
        postDetailUiState = uiState,
        modifier = modifier,
        onBackClick = { navController.navigateUp() },
        onPosterClick = {
            navController.currentBackStackEntry?.arguments?.putParcelable(
                Screen.Post.POST_POSTER_KEY,
                it
            )
            navController.navigate(Screen.Post.Poster.route)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailScreen(
    postDetailUiState: PostDetailUiState,
    modifier: Modifier,
    onBackClick: () -> Unit,
    onPosterClick: (PosterItemsState) -> Unit
) {
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        PostDetailAppBar(
            onBackClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding(),
            scrollBehavior = scrollBehavior
        )
        Box {
            when (postDetailUiState) {
                is PostDetailUiState.Failed -> {
                    PostDetailError()
                }
                PostDetailUiState.Loading -> {
                    PostDetailLoading()
                }
                is PostDetailUiState.Success -> {
                    PostDetailContent(
                        postDetailUiState = postDetailUiState,
                        onPosterClick = onPosterClick,
                    )
                }
            }
        }
    }
}

@Composable
fun PostDetailLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = 54.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun PostDetailError() {
    ErrorScreen(modifier = Modifier.fillMaxSize())
}

@Composable
fun PostDetailContent(
    postDetailUiState: PostDetailUiState.Success,
    onPosterClick: (PosterItemsState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Header(
            modifier = Modifier.fillMaxWidth(),
            postDetailHeaderState = postDetailUiState.contentState.headerState,
            onPosterClick = onPosterClick
        )
        Body(
            detailState = postDetailUiState.contentState.detailState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body(
    detailState: DetailState,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Actions(
                votesCount = detailState.votesCount,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Text(
                text = detailState.featuredDate,
                // TODO use content CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled)
                color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            Text(
                text = detailState.description,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Topics(
                topics = detailState.topics,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )

            Divider(
                color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                thickness = 0.5.dp
            )

            detailState.badge?.let {
                PostBadge(
                    badge = it,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Divider(
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    thickness = 0.5.dp
                )
            }

            Makers(
                modifier = Modifier.padding(vertical = 16.dp),
                hunter = detailState.hunter,
                makers = detailState.makers
            )

            Divider(
                color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                thickness = 0.5.dp
            )
        }
    }
}

@Composable
fun PostBadge(badge: BadgeState, modifier: Modifier) {

    Row(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = "",
                tint = badge.podiumPlace.color()
            )

            Text(
                text = badge.podiumPlace.displayablePosition,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column {

            Text(
                text = badge.title,
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                text = badge.date,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

private fun PodiumPlace.color(): Color {
    return when (this) {
        PodiumPlace.First -> Gold
        PodiumPlace.Second -> Silver
        PodiumPlace.Third,
        is PodiumPlace.OffPodium -> Bronze
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Topics(
    topics: List<String>,
    modifier: Modifier = Modifier
) {
    // TODO use content CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled)
    val contentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(topics) { topic ->

            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                shape = MaterialTheme2.shapes.medium,
                border = BorderStroke(1.dp, contentColor)
            ) {
                Text(
                    text = topic,
                    color = contentColor,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun Actions(
    votesCount: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalButton(
            modifier = Modifier.weight(1F),
            shape = MaterialTheme2.shapes.medium,
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "GET IT", maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Button(
            modifier = Modifier.weight(1F),
            shape = MaterialTheme2.shapes.medium,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropUp,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "UPVOTE $votesCount",
                modifier = Modifier.align(Alignment.CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        }
    }
}

@Composable
@Preview(
    heightDp = 2000,
    uiMode = UI_MODE_NIGHT_YES
)
fun PostDetailScreenContentDarkPreview() {
    PostDetailScreenContentPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun PostDetailScreenContentLightPreview() {
    PostDetailScreenContentPreview()
}

@Composable
fun PostDetailScreenLoadingPreview() {
    PostDetailScreenPreview(
        postDetailState = PostDetailUiState.Loading
    )
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
fun PostDetailScreenErrorDarkPreview() {
    PostDetailScreenErrorPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun PostDetailScreenErrorLightPreview() {
    PostDetailScreenErrorPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
fun PostDetailScreenLoadingDarkPreview() {
    PostDetailScreenLoadingPreview()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
fun PostDetailScreenLoadingLightPreview() {
    PostDetailScreenLoadingPreview()
}

@Composable
private fun PostDetailScreenErrorPreview() {
    PostDetailScreenPreview(
        postDetailState = PostDetailUiState.Failed("")
    )
}

@Composable
private fun PostDetailScreenContentPreview() {
    val postDetailState = PostDetailUiState.Success(
        PostDetailContentState(

            id = 1,
            headerState = PostDetailHeaderState(
                title = "ShoutOUT",
                subTitle = "Send personalized bulk SMS to customers",
                imageUrl = "https://ph-avatars.imgix.net/18699/1464c5d6-ab0f-4b26-84d6-d60cc7ee29bc?auto=format&fit=crop&crop=faces&w=100&h=100"
            ),
            detailState = DetailState(
                getItUrl = "url",
                votesCount = 14,
                featuredDate = "FEATURED 3 DAYS AGO",
                description = "As Product People, we hunt for new SaaS products every day, and they play a major role in making our lives easier — that's why we curated a list of the top 100 SaaS products that millions of users love. Subscribe to get the curation first!",
                topics = listOf("DESIGN TOOLS", "DEVELOPER TOOLS", "YOUTUBE", "DEVELOPER IDE"),
                badge = BadgeState(
                    podiumPlace = PodiumPlace.OffPodium("4"),
                    title = "#1 Product of the Day",
                    date = "Today"
                ),
                hunter = Hunter(
                    id = 1,
                    name = "Rodrigo Mendoza-Smith",
                    imageUrl = "https://ph-avatars.imgix.net/3054112/original?auto=format&fit=crop&crop=faces&w=48&h=48",
                ),
                makers = listOf(
                    Maker(
                        id = 1,
                        name = "Rodrigo Mendoza-Smith",
                        imageUrl = "https://ph-avatars.imgix.net/3054112/original?auto=format&fit=crop&crop=faces&w=48&h=48",
                    )
                )

            )
        )
    )
    PostDetailScreenPreview(postDetailState)
}

@Composable
private fun PostDetailScreenPreview(postDetailState: PostDetailUiState) {
    HuntersTheme {
        PostDetailScreen(
            postDetailUiState = postDetailState,
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            onPosterClick = {}
        )
    }
}

fun Modifier.debugBounds(width: Dp = 1.dp, color: Color = Color.Magenta) =
    border(width = width, color = color)
