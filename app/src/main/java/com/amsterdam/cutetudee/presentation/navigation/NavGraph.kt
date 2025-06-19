package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.categoryScreenRoute
import com.amsterdam.cutetudee.presentation.screens.home.homeScreenRoute
import com.amsterdam.cutetudee.presentation.screens.onBoarding.onBoardingScreenRoute
import com.amsterdam.cutetudee.presentation.screens.tasks.tasksScreenRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home) {
        onBoardingScreenRoute(navController = navController, onShowSnackBar = onShowSnackBar)
        homeScreenRoute(navController = navController, onShowSnackBar = onShowSnackBar)
        tasksScreenRoute(navController = navController, onShowSnackBar = onShowSnackBar)
        categoryScreenRoute(navController = navController, onShowSnackBar = onShowSnackBar)
    }
}