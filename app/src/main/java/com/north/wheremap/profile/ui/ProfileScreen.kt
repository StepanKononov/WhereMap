package com.north.wheremap.profile.ui

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
import com.north.wheremap.core.navigation.ChronologyRoute

@Composable
fun ProfileScreen(
    onChronologyClick: (ChronologyRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: aватарка?, logout и список собсвтенных подборок?
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
            .clickable {
                onChronologyClick(ChronologyRoute(2))
            },
    ) {
        Text(
            text = "Profile Screen",
            modifier = Modifier.fillMaxSize(),
        )
    }

}