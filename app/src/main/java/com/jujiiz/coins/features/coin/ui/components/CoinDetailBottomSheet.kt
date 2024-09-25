package com.jujiiz.coins.features.coin.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jujiiz.coins.R
import com.jujiiz.coins.features.coin.domain.models.CoinDetailEntity
import com.jujiiz.coins.features.coin.domain.models.CoinEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailBottomSheet(
    coin: CoinEntity,
    detail: CoinDetailEntity?,
    onDismiss: () -> Unit,
    onTapWebsite: (String) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                when (detail != null) {
                    true -> CoinDetailView(
                        coin = coin,
                        detail = detail
                    )

                    false -> CircularProgressIndicator(
                        modifier = Modifier.width(40.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
            HorizontalDivider()
            TextButton(
                onClick = {
                    val website = detail?.websiteUrl ?: return@TextButton
                    onTapWebsite(website)
                },
                enabled = detail != null,
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.coin_detail_go_to_website),
                    color = Color(0xFF38A0FF),
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp,
                )
            }
        }
    }
}