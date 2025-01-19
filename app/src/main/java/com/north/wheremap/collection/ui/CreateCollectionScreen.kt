package com.north.wheremap.collection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.currentStateAsState
import com.north.wheremap.R
import com.north.wheremap.core.ui.ObserveAsEvents


@Composable
fun CreateCollectionRoot(
    onConfirm: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: CreateCollectionViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleState = LocalLifecycleOwner.current.lifecycle.currentStateAsState()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            CreateCollectionEvents.Confirm -> onConfirm()
            CreateCollectionEvents.NavigateBack -> onNavigateBack()
        }
    }

    CreateCollectionContent(
        state = state.value,
        onConfirm = {
            viewModel.runAction(lifecycleState.value, CreateCollectionActions.ConfirmSaveCollection)
        },
        onCollectionNameChange = { newName ->
            viewModel.runAction(
                lifecycleState.value,
                CreateCollectionActions.UpdateCollectionName(newName)
            )
        },
        onCollectionDescriptionChange = { newDescription ->
            viewModel.runAction(
                lifecycleState.value,
                CreateCollectionActions.UpdateCollectionDescription(newDescription)
            )
        },
        onCollectionCityChange = { newCity ->
            viewModel.runAction(
                lifecycleState.value,
                CreateCollectionActions.UpdateCollectionCity(newCity)
            )
        },
        onCollectionPrivacyChange = { newPrivacy ->
            viewModel.runAction(
                lifecycleState.value,
                CreateCollectionActions.UpdateCollectionPrivacy(newPrivacy)
            )
        },
        onNavigateBack = onNavigateBack
    )

}

// Действия только в RESUMED для исключения дублирования сохранения точек в бд
fun CreateCollectionViewModel.runAction(state: Lifecycle.State, actions: CreateCollectionActions) {
    if (state == Lifecycle.State.RESUMED) {
        onAction(actions)
    }
}

@Composable
fun CreateCollectionContent(
    state: CreateCollectionScreenState,
    onConfirm: () -> Unit,
    onNavigateBack: () -> Unit,
    onCollectionNameChange: (String) -> Unit,
    onCollectionDescriptionChange: (String) -> Unit,
    onCollectionCityChange: (String) -> Unit,
    onCollectionPrivacyChange: (Boolean) -> Unit,
) {
    val isNameValid = state.collectionName.isNotBlank()
    Scaffold(
        topBar = { CreateCollectionTopBar(onConfirm = {
            if (isNameValid) {
                onConfirm()
            }
        }, onNavigateBack ) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                EditableField(
                    value = state.collectionName,
                    label = stringResource(R.string.collection_name_text_hint),
                    onValueChange = onCollectionNameChange,
                    imeAction = ImeAction.Next
                )
                if (!isNameValid) {
                    Text(
                        text = stringResource(R.string.error_empty_name),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            item {
                EditableField(
                    value = state.collectionDescription,
                    label = stringResource(R.string.collection_description_text_hint),
                    onValueChange = onCollectionDescriptionChange,
                    imeAction = ImeAction.Next
                )
            }
            item {
                EditableField(
                    value = state.collectionCity ?: "",
                    label = stringResource(R.string.collection_city_text_hint),
                    onValueChange = onCollectionCityChange,
                    imeAction = ImeAction.Done
                )
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                        Checkbox(
                            checked = state.isCollectionPrivate,
                            onCheckedChange = onCollectionPrivacyChange,
                        )
                    }

                    Text(stringResource(R.string.collection_privacy_question_text_hint))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCollectionTopBar(onConfirm: () -> Unit, onNavigateBack: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.add_to_collection_screen_title)) },

        navigationIcon = { IconButton(onClick =onNavigateBack ){
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null, )
        }},
        actions = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.done_btn_text))
            }
        }
    )
}
