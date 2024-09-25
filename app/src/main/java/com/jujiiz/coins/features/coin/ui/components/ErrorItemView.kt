package com.jujiiz.coins.features.coin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jujiiz.coins.R

@Composable
fun ErrorItemView(onTapTryAgain: () -> Unit) {
    Box(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 0.dp,
            bottom = 12.dp,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.main_screen_error_content),
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.main_screen_try_again_button),
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
                color = Color(0xFF38A0FF),
                modifier = Modifier.clickable(onClick = onTapTryAgain)
            )
        }
    }
}