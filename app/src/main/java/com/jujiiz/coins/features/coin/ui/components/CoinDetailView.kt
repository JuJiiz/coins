package com.jujiiz.coins.features.coin.ui.components

import android.content.res.Configuration
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jujiiz.coins.R
import com.jujiiz.coins.core.theme.CoinsTheme
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.CoinEntity

@Composable
fun CoinDetailView(
    coin: CoinEntity,
    detail: CoinDetailEntity,
) {
    val description = detail.description
    Surface(
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            /*modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())*/
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                Box(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = detail.colorInt
                                        ?.let(::Color)
                                        ?: MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                )
                            ) {
                                append(coin.fullName)
                            }
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 16.sp,
                                )
                            ) {
                                append("(${coin.shortName})")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 12.sp,
                                )
                            ) {
                                append("PRICE")
                            }
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 12.sp,
                                )
                            ) {
                                append("$ ${DecimalFormat("#,##0.00").format(coin.price)}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 12.sp,
                                )
                            ) {
                                append("PRICE")
                            }
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 12.sp,
                                )
                            ) {
                                append("$ ${detail.marketCapLabel}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            when (description != null) {
                true -> Column(
                    modifier = Modifier
                        .heightIn(0.dp,400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = detail.description,
                        color = Color(0xFF999999),
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }

                false -> Text(
                    text = stringResource(id = R.string.coin_detail_no_description),
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }

        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CoinDetailViewPreview() {
    CoinsTheme {
        CoinDetailView(
            coin = CoinEntity(
                id = "1",
                fullName = "COIN 1",
                shortName = "C1",
                price = 5000.0,
                change = 0.5,
                imageUrl = "https://loremflickr.com/300/300",
            ),
            detail = CoinDetailEntity(
                colorInt = "#f7931A".toColorInt(),
                marketCapLabel = "1.02 million",
                description = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.",
                websiteUrl = "https://www.lipsum.com/",
            )
        )
    }
}