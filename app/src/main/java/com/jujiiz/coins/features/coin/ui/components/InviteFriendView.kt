package com.jujiiz.coins.features.coin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jujiiz.coins.R

@Composable
fun InviteFriendView(onTapInviteFriend: () -> Unit) {
    Box(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 0.dp,
            bottom = 12.dp,
        )
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color(0xFFC5E6FF)),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color.White,
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_present),
                        contentDescription = "Invite Friend Icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .width(24.dp)
                            .height(24.dp),
                    )
                }
                ClickableText(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.main_screen_invite_friend_quote))
                        append(" ")
                        val spanText = stringResource(id = R.string.main_screen_invite_friend_span)
                        withStyle(style = SpanStyle(Color(0xFF649FFC))) {
                            pushStringAnnotation(spanText, spanText)
                            append(spanText)
                            pop()
                        }
                    },
                    onClick = {
                        // TODO
                        onTapInviteFriend()
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                    ),
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}
