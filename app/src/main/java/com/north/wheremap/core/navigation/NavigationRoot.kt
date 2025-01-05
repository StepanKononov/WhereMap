package com.north.wheremap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.north.wheremap.MainScreen
import com.north.wheremap.collection.ui.AddToCollectionRoot
import com.north.wheremap.feeds.ui.FeedScreen
import com.north.wheremap.map.location.Location
import com.north.wheremap.map.ui.MapScreenRoot
import com.north.wheremap.map.ui.chronology.ChronologyScreen
import com.north.wheremap.profile.ui.ProfileScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainGraphRoute,
    ) {
        // TODO: вложенный граф авторизации
        composable<ChronologyRoute> {
            ChronologyScreen()
        }

        composable<AddToCollectionRoute>(
            typeMap = mapOf(
                typeOf<Location>() to CustomNavType.LocationType
            )
        ) {
            AddToCollectionRoot()
        }

        composable<MainGraphRoute> {
            MainScreen(
                openChronology = { chronology ->
                    navController.navigate(chronology)
                },
                openAddToCollection = { addToCollection ->
                    navController.navigate(addToCollection)
                }
            )
        }
    }
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
    openChronology: (ChronologyRoute) -> Unit,
    openAddToCollection: (AddToCollectionRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MapRoute,
        modifier = modifier
    ) {
        composable<MapRoute> {
            MapScreenRoot(
                onAddNewPoint = openAddToCollection
            )
        }
        composable<FeedsListRoute> {
            FeedScreen(
                onChronologyClick = { chronology ->
                    openChronology(chronology)
                }
            )
        }
        composable<ProfileRoute> {
            ProfileScreen(
                onChronologyClick = { chronology ->
                    openChronology(chronology)
                }
            )
        }
    }
}