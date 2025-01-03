package com.north.wheremap.feeds.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.north.wheremap.core.navigation.Chronology

@Composable
fun FeedScreen(
    onChronologyClick: (Chronology) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: лента с подборками других пользователей
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
            .clickable {
                onChronologyClick(Chronology(1))
            },
    ) {
        Text(
            text = "Feed Screen",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}
