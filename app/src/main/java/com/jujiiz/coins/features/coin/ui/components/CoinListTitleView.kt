package com.jujiiz.coins.features.coin.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jujiiz.coins.R
import com.jujiiz.coins.core.FULL_SIZE_BREAKPOINT_DP

@Composable
fun CoinListTitleView() {
    BoxWithConstraints(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 0.dp,
            bottom = 12.dp,
        )
    ) {
        val isFullSize = maxWidth >= FULL_SIZE_BREAKPOINT_DP.dp
        Text(
            text = stringResource(id = R.string.main_screen_coin_list_title),
            textAlign = if (isFullSize) TextAlign.Center else TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.W700,
        )
    }
}