package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.amsterdam.cutetudee.data.local.datastore.AppPreferencesDataStore
import com.amsterdam.cutetudee.presentation.component.CuteTudeeScaffold
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.categoryScreenRoute
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.categoryDetailsScreenRoute
import com.amsterdam.cutetudee.presentation.screens.home.homeScreenRoute
import com.amsterdam.cutetudee.presentation.screens.onBoarding.onBoardingScreenRoute
import com.amsterdam.cutetudee.presentation.screens.splash.splashScreenRoute
import com.amsterdam.cutetudee.presentation.screens.tasks.tasksScreenRoute
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }
val LocalSnackBarState =
    compositionLocalOf<CustomSnackBarStatus> { error("No SnackBarState provided") }
val LocalThemeState =
    compositionLocalOf<MutableState<TudeeThemeMode>> { error("No TaskManagementState provided") }


@Composable
fun NavGraph(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    val navController = rememberNavController()
    val snackBarState = remember { CustomSnackBarStatus.Success }
    val context = LocalContext.current
    val appPreferences = remember { AppPreferencesDataStore(context) }
    val themeMode = rememberSaveable {
        mutableStateOf(if (appPreferences.isDarkTheme()) TudeeThemeMode.DARK else TudeeThemeMode.LIGHT)
    }
    val systemUiController = rememberSystemUiController()
    val darkIcons = themeMode.value == TudeeThemeMode.LIGHT

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, darkIcons = darkIcons
        )
    }

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalSnackBarState provides snackBarState,
        LocalThemeState provides themeMode
    ) {
        CuteTudeeTheme(
            isDarkTheme = themeMode.value == TudeeThemeMode.DARK
        ) {
            CuteTudeeScaffold(
                modifier = Modifier
                    .background(AppTheme.color.surfaceHigh)
                    .navigationBarsPadding(),
                bottomBar = {
                    CuteTudeeBottomNavigation(
                        currentDestination = navController.currentDestination,
                        onNavigate = { navController.navigate(it.screen) })
                },
                contentBackground = AppTheme.color.surfaceHigh
            ) {
                NavHost(navController = navController, startDestination = Screen.Splash) {
                    splashScreenRoute()
                    onBoardingScreenRoute(
                        onShowSnackBar = onShowSnackBar
                    )
                    homeScreenRoute(onShowSnackBar = onShowSnackBar)
                    tasksScreenRoute(navController = navController, onShowSnackBar = onShowSnackBar)
                    categoryScreenRoute(
                        navController = navController, onShowSnackBar = onShowSnackBar
                    )
                    categoryDetailsScreenRoute(
                        navController = navController, onShowSnackBar = onShowSnackBar
                    )
                }
            }
        }
    }
}

enum class TudeeThemeMode(val value: Boolean) {
    DARK(true), LIGHT(false)
}