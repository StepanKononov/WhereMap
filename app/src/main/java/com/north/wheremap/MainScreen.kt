package com.north.wheremap

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.north.wheremap.core.navigation.AddToCollectionRoute
import com.north.wheremap.core.navigation.ChronologyRoute
import com.north.wheremap.core.navigation.FeedsListRoute
import com.north.wheremap.core.navigation.MainNavGraph
import com.north.wheremap.core.navigation.MapRoute
import com.north.wheremap.core.navigation.ProfileRoute

@Composable
fun MainScreen(
    openChronology: (ChronologyRoute) -> Unit,
    openAddToCollection: (AddToCollectionRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val items = listOf(
        Triple(R.string.nav_tab_map, Icons.Default.LocationOn, MapRoute),
        Triple(R.string.nav_tab_feed, Icons.Default.Home, FeedsListRoute),
        Triple(R.string.nav_tab_profile, Icons.Default.Person, ProfileRoute),
    )

    val showDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            // TODO: коряво) но если высоты совпадают то пойдет. Нужно делать нормальный диалог
            AnimatedVisibility(
                visible = showDialog.value,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            ) {
                SavePointDialog(
                    onSave = {
                        // Логика сохранения
                        showDialog.value = false
                    },
                    onClose = {
                        showDialog.value = false
                    }
                )
            }

            AnimatedVisibility(
                visible = !showDialog.value,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                val entry by navController.currentBackStackEntryAsState()
                val currentDestination = entry?.destination
                NavigationBar {
                    items.forEach { (label, icon, destination) ->
                        NavigationBarItem(
                            label = { Text(stringResource(label)) },
                            icon = { Icon(icon, contentDescription = stringResource(label)) },
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(destination::class)
                            } == true,
                            onClick = { navController.navigate(destination) }
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        MainNavGraph(
            navController,
            openChronology,
            openAddToCollection = {
                showDialog.value = true
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
                ),
        )
    }
}


@Composable
fun SavePointDialog(
    onSave: () -> Unit,
    onClose: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSave
                ) {
                    Text("Сохранить")
                }

                OutlinedButton(onClick = onClose) {
                    Text("Закрыть")
                }
            }
        }
    }
}