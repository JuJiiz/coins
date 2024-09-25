package com.jujiiz.coins.features.coin.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jujiiz.coins.core.reachedBottom
import com.jujiiz.coins.core.theme.CoinsTheme
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinListEntity

@Composable
fun DisplayedCoinListView(
    feedItems: List<DisplayedCoinListEntity>,
    isFullSize: Boolean,
    onTapInviteFriend: () -> Unit,
    onTapTryAgain: () -> Unit,
    onLoadMore: () -> Unit,
    onTapCoin: (CoinEntity) -> Unit,
) {
    if (isFullSize) {
        val columnCount = 3

        val gridState = rememberLazyGridState()
        val reachedBottom: Boolean by remember {
            derivedStateOf { gridState.reachedBottom() }
        }

        LaunchedEffect(reachedBottom) {
            if (reachedBottom) onLoadMore()
        }

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(columnCount),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(
                count = feedItems.size,
                span = { index ->
                    val feedItem = feedItems[index]
                    if (feedItem.isAbleToFullSpan) {
                        GridItemSpan(columnCount)
                    } else {
                        GridItemSpan(1)
                    }
                }
            ) { index ->
                when (val feedItem = feedItems[index]) {
                    is DisplayedCoinListEntity.CoinItem -> CoinListItemView(
                        coin = feedItem.entity,
                        onTap = onTapCoin
                    )

                    DisplayedCoinListEntity.CoinTitle -> CoinListTitleView()
                    DisplayedCoinListEntity.Error -> ErrorItemView(onTapTryAgain = onTapTryAgain)
                    DisplayedCoinListEntity.InviteFriend -> InviteFriendView(onTapInviteFriend = onTapInviteFriend)
                    DisplayedCoinListEntity.Loading -> LoadingItemView()
                    is DisplayedCoinListEntity.TopThreeCoin -> TopThreeCoinsView(
                        topThree = feedItem,
                        onTap = onTapCoin,
                    )

                    is DisplayedCoinListEntity.JustSpace -> Box(
                        modifier = Modifier
                            .height(feedItem.measureInDp.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    } else {
        val listState = rememberLazyListState()
        val reachedBottom: Boolean by remember {
            derivedStateOf { listState.reachedBottom() }
        }

        LaunchedEffect(reachedBottom) {
            if (reachedBottom) onLoadMore()
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(count = feedItems.size) { index ->
                when (val feedItem = feedItems[index]) {
                    is DisplayedCoinListEntity.CoinItem -> CoinListItemView(
                        coin = feedItem.entity,
                        onTap = onTapCoin
                    )

                    DisplayedCoinListEntity.CoinTitle -> CoinListTitleView()
                    DisplayedCoinListEntity.Error -> ErrorItemView(onTapTryAgain = onTapTryAgain)
                    DisplayedCoinListEntity.InviteFriend -> InviteFriendView(onTapInviteFriend = onTapInviteFriend)
                    DisplayedCoinListEntity.Loading -> LoadingItemView()
                    is DisplayedCoinListEntity.TopThreeCoin -> TopThreeCoinsView(
                        topThree = feedItem,
                        onTap = onTapCoin,
                    )

                    is DisplayedCoinListEntity.JustSpace -> Box(
                        modifier = Modifier
                            .height(feedItem.measureInDp.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PagePreview() {
    val coins = List(3) { index ->
        CoinEntity(
            id = index.toString(),
            fullName = "COIN $index",
            shortName = "C$index",
            price = 5000.0,
            change = 0.5,
            imageUrl = "https://loremflickr.com/300/300",
        )
    }
    val feedItems = buildList {
        add(DisplayedCoinListEntity.TopThreeCoin(coins[0], coins[1], coins[2]))
        add(DisplayedCoinListEntity.CoinTitle)
        addAll(coins.map(DisplayedCoinListEntity::CoinItem))
        add(DisplayedCoinListEntity.InviteFriend)
        add(DisplayedCoinListEntity.Error)
        add(DisplayedCoinListEntity.Loading)
    }
    CoinsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DisplayedCoinListView(
                feedItems = feedItems,
                isFullSize = false,
                onTapInviteFriend = { },
                onTapTryAgain = { },
                onLoadMore = { },
                onTapCoin = { _ -> },
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    device = Devices.PIXEL_TABLET,
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET,
)
@Composable
fun PagePreviewTablet() {
    val coins = List(3) { index ->
        CoinEntity(
            id = index.toString(),
            fullName = "COIN $index",
            shortName = "C$index",
            price = 5000.0,
            change = 0.5,
            imageUrl = "https://loremflickr.com/300/300",
        )
    }
    val feedItems = buildList {
        add(DisplayedCoinListEntity.TopThreeCoin(coins[0], coins[1], coins[2]))
        add(DisplayedCoinListEntity.CoinTitle)
        addAll(coins.map(DisplayedCoinListEntity::CoinItem))
        add(DisplayedCoinListEntity.InviteFriend)
        add(DisplayedCoinListEntity.Error)
        add(DisplayedCoinListEntity.Loading)
    }
    CoinsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                DisplayedCoinListView(
                    feedItems = feedItems,
                    isFullSize = true,
                    onTapInviteFriend = { },
                    onTapTryAgain = { },
                    onLoadMore = { },
                    onTapCoin = { _ -> },
                )
            }
        }
    }
}