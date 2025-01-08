package com.north.wheremap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.north.wheremap.MainScreen
import com.north.wheremap.collection.ui.AddToCollectionRoot
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.feeds.ui.FeedScreen
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

        authGraph(navController)
        // TODO: вложенный граф авторизации
        composable<ChronologyRoute> {
            ChronologyScreen()
        }

        composable<AddToCollectionRoute>(
            typeMap = mapOf(
                typeOf<Location>() to CustomNavType.LocationType
            )
        ) {
            AddToCollectionRoot(
                onConfirm = {
                    navController.navigateUp()
                }
            )
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

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            /*
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            ) */
        }
        composable(route = "register") {
            /*
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )*/
        }
        composable("login") {
            /*
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
            */
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