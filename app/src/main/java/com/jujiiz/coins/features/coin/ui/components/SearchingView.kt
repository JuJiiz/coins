package com.jujiiz.coins.features.coin.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jujiiz.coins.R
import com.jujiiz.coins.core.theme.CoinsTheme

@Composable
fun SearchingView(
    state: State<String>,
    onChanged: (String) -> Unit,
    onTapClear: () -> Unit,
) {
    val text by remember { state }

    TextField(
        value = text,
        onValueChange = onChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(text = "Search")
        },
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = "Search Icon",
            )
        },
        trailingIcon = {
            if (text.isNotBlank()) ClearButton(onTapClear)
        },
    )
}

@Composable
private fun ClearButton(onTap: () -> Unit) {
    IconButton(
        onClick = onTap,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clear_button),
            contentDescription = "Clear Searching Field Button",
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchingView(
    state: MutableState<String> = mutableStateOf("eiei"),
) {
    CoinsTheme {
        SearchingView(
            state = state,
            onChanged = { _ -> },
            onTapClear = { },
        )
    }
}

