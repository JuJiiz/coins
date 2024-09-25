package com.jujiiz.coins.features.coin.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.jujiiz.coins.core.FULL_SIZE_BREAKPOINT_DP
import com.jujiiz.coins.core.theme.CoinsTheme
import com.jujiiz.coins.features.coin.domain.models.CoinDetailBottomSheetState
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinFeedEntity
import com.jujiiz.coins.features.coin.ui.components.CoinDetailBottomSheet
import com.jujiiz.coins.features.coin.ui.components.DisplayedCoinListView
import com.jujiiz.coins.features.coin.ui.components.EmptyView
import com.jujiiz.coins.features.coin.ui.components.SearchingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinsTheme {
                MainScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainScreen() {
        val context = LocalContext.current

        val loadingState by viewModel.stateLoading

        val feedTypeState by viewModel.flowDisplayedCoinList.collectAsState(initial = DisplayedCoinFeedEntity.getDefault())

        val pullRefreshState = rememberPullRefreshState(
            refreshing = loadingState,
            onRefresh = viewModel::refreshList
        )

        var bottomSheetState: CoinDetailBottomSheetState by remember {
            mutableStateOf(
                CoinDetailBottomSheetState.Hide
            )
        }

        if (bottomSheetState is CoinDetailBottomSheetState.Show) {
            val state = bottomSheetState as CoinDetailBottomSheetState.Show
            val detail by viewModel.stateCoinDetail
            CoinDetailBottomSheet(
                coin = state.coin,
                detail = detail,
                onDismiss = {
                    bottomSheetState = CoinDetailBottomSheetState.Hide
                    viewModel.clearCoinDetail()
                },
                onTapWebsite = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    ContextCompat.startActivity(context, intent, null)
                },
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    SearchingView(
                        state = viewModel.flowSearchingText.collectAsState(initial = ""),
                        onChanged = viewModel::updateSearchingText,
                        onTapClear = viewModel::clearSearchText,
                    )
                }
                HorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(state = pullRefreshState)
                        .weight(1f)
                ) {
                    when (val feedType = feedTypeState) {
                        DisplayedCoinFeedEntity.EmptyContent -> EmptyView()
                        is DisplayedCoinFeedEntity.HasContent -> BoxWithConstraints {
                            DisplayedCoinListView(
                                feedItems = feedType.items,
                                isFullSize = maxWidth >= FULL_SIZE_BREAKPOINT_DP.dp,
                                onTapInviteFriend = {
                                    val intent =
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://careers.lmwn.com")
                                        )
                                    ContextCompat.startActivity(context, intent, null)
                                },
                                onTapTryAgain = {
                                    viewModel.tryToFetchAgain()
                                },
                                onLoadMore = {
                                    viewModel.requestNextPage()
                                },
                                onTapCoin = { coin ->
                                    viewModel.fetchCoinDetail(coin.id)
                                    bottomSheetState = CoinDetailBottomSheetState.Show(coin)
                                }
                            )
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = viewModel.stateLoading.value,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }
        }
    }
}




