package com.north.wheremap

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.north.wheremap.core.navigation.Chronology
import com.north.wheremap.core.navigation.FeedsList
import com.north.wheremap.core.navigation.MainNavGraph
import com.north.wheremap.core.navigation.Map
import com.north.wheremap.core.navigation.Profile

@Composable
fun MainScreen(
    openChronology: (Chronology) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val items = listOf(
        Triple(R.string.nav_tab_map, Icons.Default.LocationOn, Map),
        Triple(R.string.nav_tab_feed, Icons.Default.Home, FeedsList),
        Triple(R.string.nav_tab_profile, Icons.Default.Person, Profile),
    )
    Scaffold(
        bottomBar = {
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
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        MainNavGraph(
            navController,
            openChronology,
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
