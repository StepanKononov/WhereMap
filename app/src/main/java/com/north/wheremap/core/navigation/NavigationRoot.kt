package com.north.wheremap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.north.wheremap.MainScreen
import com.north.wheremap.feeds.ui.FeedScreen
import com.north.wheremap.map.ui.MapScreen
import com.north.wheremap.map.ui.chronology.ChronologyScreen
import com.north.wheremap.profile.ui.ProfileScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainGraph
    ) {
        // TODO: вложенный граф авторизации
        composable<Chronology> {
            ChronologyScreen()
        }

        composable<MainGraph> {
            MainScreen(
                openChronology = { chronology ->
                    navController.navigate(chronology)
                }
            )
        }
    }
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
    openChronology: (Chronology) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Map,
        modifier = modifier
    ) {
        composable<FeedsList> {
            FeedScreen(
                onChronologyClick = { chronology ->
                    openChronology(chronology)
                }
            )
        }
        composable<Map> {
            MapScreen()
        }
        composable<Profile> {
            ProfileScreen(
                onChronologyClick = { chronology ->
                    openChronology(chronology)
                }
            )
        }
    }
}