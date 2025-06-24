package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.categoryScreenRoute
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.categoryDetailsScreenRoute
import com.amsterdam.cutetudee.presentation.screens.home.homeScreenRoute
import com.amsterdam.cutetudee.presentation.screens.onBoarding.onBoardingScreenRoute
import com.amsterdam.cutetudee.presentation.screens.splash.splashScreenRoute
import com.amsterdam.cutetudee.presentation.screens.tasks.tasksScreenRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Splash) {
        splashScreenRoute()
        onBoardingScreenRoute(onShowSnackBar = onShowSnackBar)
        homeScreenRoute(onShowSnackBar = onShowSnackBar)
        tasksScreenRoute(onShowSnackBar = onShowSnackBar)
        categoryScreenRoute( onShowSnackBar = onShowSnackBar)
        categoryDetailsScreenRoute(onShowSnackBar = onShowSnackBar)
    }
}