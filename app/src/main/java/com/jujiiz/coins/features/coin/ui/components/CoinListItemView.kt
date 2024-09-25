package com.jujiiz.coins.features.coin.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jujiiz.coins.R
import com.jujiiz.coins.features.coin.domain.models.CoinEntity

@Composable
fun CoinListItemView(
    coin: CoinEntity,
    onTap: (CoinEntity) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 0.dp,
                bottom = 12.dp,
            )
            .clickable {
                onTap(coin)
            }
    ) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(all = 16.dp),
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
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = coin.fullName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = coin.shortName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        color = Color(0xFF999999),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "$${DecimalFormat("#,##0.00000").format(coin.price)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
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
    }
}