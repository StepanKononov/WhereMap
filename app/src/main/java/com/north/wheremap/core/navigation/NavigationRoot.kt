package com.north.wheremap.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.north.wheremap.MainScreen
import com.north.wheremap.auth.ui.IntroScreenRoot
import com.north.wheremap.auth.ui.login.LoginScreenRoot
import com.north.wheremap.auth.ui.register.RegisterScreenRoot
import com.north.wheremap.collection.ui.AddToCollectionRoot
import com.north.wheremap.collection.ui.CreateCollectionRoot
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.feeds.ui.FeedScreen
import com.north.wheremap.map.ui.MapScreenRoot
import com.north.wheremap.map.ui.chronology.ChronologyScreenRoot
import com.north.wheremap.profile.ui.ProfileScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationRoot(
    isLoggedIn: Boolean,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) MainGraphRoute else AuthRoute
    ) {

        authGraph(navController)
        composable<ChronologyRoute>(
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            ChronologyScreenRoot(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<AddToCollectionRoute>(
            typeMap = mapOf(
                typeOf<Location>() to CustomNavType.LocationType
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            AddToCollectionRoot(
                onConfirm = {
                    navController.navigateUp()
                },
                onClickCreateCollection = {
                    navController.navigate(CreateCollectionRoute)
                }
            )
        }

        composable<CreateCollectionRoute>(
            typeMap = mapOf(
                typeOf<Location>() to CustomNavType.LocationType
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            CreateCollectionRoot(
                onConfirm = {
                    navController.navigateUp()
                },
                onNavigateBack = {
                    navController.popBackStack()
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
                },
                onLogoutClick = {
                    navController.navigate(AuthRoute) {
                        popUpTo(MainGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthRoute>(
        startDestination = IntroRoute,
    ) {
        composable<IntroRoute> {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate(RegisterRoute)
                },
                onSignInClick = {
                    navController.navigate(LoginRoute)
                }
            )
        }
        composable<RegisterRoute> {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo(RegisterRoute) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(MainGraphRoute)
                }
            )
        }
        composable<LoginRoute> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(MainGraphRoute) {
                        popUpTo(AuthRoute) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(RegisterRoute) {
                        popUpTo(LoginRoute) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
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
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MapRoute,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
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
                },
                onLogout = onLogoutClick
            )
        }
    }
}