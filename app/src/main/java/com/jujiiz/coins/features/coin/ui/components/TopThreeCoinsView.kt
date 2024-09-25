package com.jujiiz.coins.features.coin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jujiiz.coins.R
import com.jujiiz.coins.core.FULL_SIZE_BREAKPOINT_DP
import com.jujiiz.coins.features.coin.domain.models.CoinEntity
import com.jujiiz.coins.features.coin.domain.models.DisplayedCoinListEntity

@Composable
fun TopThreeCoinsView(
    topThree: DisplayedCoinListEntity.TopThreeCoin,
    onTap: (CoinEntity) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 0.dp,
                bottom = 12.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        val isFullSize = maxWidth >= FULL_SIZE_BREAKPOINT_DP.dp
        Column {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.main_screen_top_three_title_first_part))
                    append(" ")
                    withStyle(
                        style = SpanStyle(color = Color(0xFFA22525)),
                    ) {
                        append(stringResource(id = R.string.main_screen_top_three_title_second_part))
                    }
                    append(" ")
                    append(stringResource(id = R.string.main_screen_top_three_title_third_part))
                },
                textAlign = if (isFullSize) TextAlign.Center else TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .widthIn(0.dp, (FULL_SIZE_BREAKPOINT_DP / 2).dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onTap(topThree.first)
                            }
                    ) {
                        TopThreeCoinItemView(topThree.first)
                    }
                    Box(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onTap(topThree.second)
                            }
                    ) {
                        TopThreeCoinItemView(topThree.second)
                    }
                    Box(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onTap(topThree.third)
                            }
                    ) {
                        TopThreeCoinItemView(topThree.third)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopThreeCoinItemView(coin: CoinEntity) {
    Card(
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coin.imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "Coin Icon",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
            )
            Box(modifier = Modifier.height(16.dp))
            Text(
                text = coin.shortName,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W700,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = coin.fullName,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF999999),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = if (coin.change < 0) R.drawable.ic_arrow_down_red else R.drawable.ic_arrow_up_green),
                    contentDescription = "Change Icon"
                )
                Text(
                    text = "${coin.change}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W700,
                    color = if (coin.change < 0) Color(0xFFF82D2D) else Color(0xFF13BC24),
                )
            }
        }
    }
}