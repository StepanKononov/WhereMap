package com.north.wheremap.collection.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.currentStateAsState
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.north.wheremap.R
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.core.ui.ObserveAsEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


@Composable
fun AddToCollectionRoot(
    onConfirm: () -> Unit,
    onClickCreateCollection: () -> Unit = {},
    viewModel: AddToCollectionViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleState = LocalLifecycleOwner.current.lifecycle.currentStateAsState()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            AddToCollectionEvents.Confirm -> onConfirm()
            AddToCollectionEvents.NavigateToCreateCollectionScreen -> {
                onClickCreateCollection()
            }
        }
    }

    AddToCollectionContent(
        state = state.value,
        onConfirm = {
            viewModel.runAction(lifecycleState.value, AddToCollectionActions.ConfirmSavePoint)
        },
        onCreateNewCollection = {
            viewModel.runAction(lifecycleState.value, AddToCollectionActions.CreateNewCollection)
        },
        onPointNameChange = { newName ->
            viewModel.runAction(
                lifecycleState.value,
                AddToCollectionActions.UpdatePointName(newName)
            )
        },
        onPointDescriptionChange = { newDescription ->
            viewModel.runAction(
                lifecycleState.value,
                AddToCollectionActions.UpdatePointDescription(newDescription)
            )
        },
        onSelectCollection = { collectionId ->
            viewModel.runAction(
                lifecycleState.value,
                AddToCollectionActions.SetSelectedCollection(collectionId)
            )
        }
    )

}

// Действия только в RESUMED для исключения дублирования сохранения точек в бд
fun AddToCollectionViewModel.runAction(state: Lifecycle.State, actions: AddToCollectionActions) {
    if (state == Lifecycle.State.RESUMED) {
        onAction(actions)
    }
}

@Composable
fun AddToCollectionContent(
    state: AddToCollectionScreenState,
    onConfirm: () -> Unit,
    onCreateNewCollection: () -> Unit,
    onPointNameChange: (String) -> Unit,
    onPointDescriptionChange: (String) -> Unit,
    onSelectCollection: (String) -> Unit
) {
    Scaffold(
        topBar = { AddToCollectionTopBar(onConfirm = onConfirm) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                MapPreview(
                    point = state.point,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            item {
                Spacer(Modifier.height(16.dp))
                EditableField(
                    value = state.pointName,
                    label = stringResource(R.string.point_name_text_hint),
                    onValueChange = onPointNameChange,
                    imeAction = ImeAction.Next
                )
            }
            item {
                Spacer(Modifier.height(16.dp))
                EditableField(
                    value = state.pointDescription,
                    label = stringResource(R.string.point_description_text_hint),
                    onValueChange = onPointDescriptionChange,
                    imeAction = ImeAction.Done
                )
            }
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.collection_section_title),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            item {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onCreateNewCollection,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.size(8.dp))
                        Text(stringResource(R.string.create_new_collection_btn_text))
                    }
                }
            }
            items(
                items = state.collections,
                key = { collection -> collection.id }
            ) { collection ->
                CollectionItem(
                    collection = collection,
                    isSelected = state.selectedCollectionId == collection.id,
                    onSelectItem = onSelectCollection
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCollectionTopBar(onConfirm: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.add_to_collection_screen_title)) },
        actions = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.done_btn_text))
            }
        }
    )
}

@Composable
fun MapPreview(point: Location, color: Color) {
    val center = Point.fromLngLat(point.long, point.lat)
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(center)
            zoom(12.0)
            pitch(0.0)
            bearing(0.0)
        }
    }
    val mapState = rememberMapState {
        gesturesSettings = gesturesSettings
            .toBuilder()
            .setScrollEnabled(false)
            .setPitchEnabled(false)
            .setRotateEnabled(false)
            .setDoubleTapToZoomInEnabled(false)
            .setQuickZoomEnabled(false)
            .setRotateEnabled(false)
            .setPinchScrollEnabled(false)
            .setPinchToZoomEnabled(false)
            .setSimultaneousRotateAndPinchToZoomEnabled(false)
            .build()
    }

    MapboxMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(40)),
        mapViewportState = mapViewportState,
        scaleBar = {},
        logo = {},
        attribution = {},
        style = {
            GenericStyle(style = if (isSystemInDarkTheme()) Style.DARK else Style.LIGHT)
        },
        compass = {
            Compass(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            )
        },
        mapState = mapState
    ) {
        CircleAnnotation(center) {
            circleRadius = 8.0
            circleColor = color
        }
    }
}

@Composable
fun EditableField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onDone = { focusManager.clearFocus() }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CollectionItem(
    collection: CollectionUI,
    isSelected: Boolean,
    onSelectItem: (String) -> Unit
) {
    Column {
        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = collection.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (collection.isPrivate)
                        stringResource(R.string.private_collection)
                    else
                        stringResource(R.string.public_collection),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected,
                onClick = { onSelectItem(collection.id) }
            )
        }
        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
    }
}
