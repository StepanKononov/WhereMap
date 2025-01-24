package com.north.wheremap.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.north.wheremap.R
import com.north.wheremap.collection.ui.CollectionUI
import com.north.wheremap.collection.ui.MapPreview
import com.north.wheremap.core.navigation.ChronologyRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onChronologyClick: (ChronologyRoute) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val exampleCollection: CollectionUI =  CollectionUI("1","JapanTrip","Out trip to Japan!!!", "トキオ", true)
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                ProfileScreenEvents.NavigateToAuth -> onLogout()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                actions = {
                    IconButton(onClick = { viewModel.onAction(ProfileScreenActions.Logout) }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cъебаться"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(state.value.collections, key = { it.id }) { collection ->
                CollectionItem(
                    collection = collection,
                    onClick = { onChronologyClick(ChronologyRoute(collection.id)) }
                )
            }

            item {
                CollectionItem(
                    collection = exampleCollection,
                    onClick = { onChronologyClick(ChronologyRoute(exampleCollection.id)) }
                )
            }
        }
    }
}

@Composable
fun CollectionItem(
    collection: CollectionUI,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            ) {
                // Placeholder for image
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = collection.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = collection.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (collection.isPrivate)
                        stringResource(R.string.private_collection)
                    else
                        stringResource(R.string.public_collection),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
