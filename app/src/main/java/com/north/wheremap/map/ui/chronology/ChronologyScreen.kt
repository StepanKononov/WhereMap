package com.north.wheremap.map.ui.chronology

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChronologyScreen(viewModel: ChronologyViewModel = hiltViewModel()) {
    // TODO: загружать список точек по id, и отображать их на карте
    val id = viewModel.id
    Text(text = "ChronologyRoute Screen with ID: $id")
}
