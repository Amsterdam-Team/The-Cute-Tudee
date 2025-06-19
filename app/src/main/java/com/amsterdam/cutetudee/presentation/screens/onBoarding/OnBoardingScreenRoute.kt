package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.onBoardingScreenRoute(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    composable<Screen.OnBoarding> {
        OnBoardingScreen(navController = navController, onShowSnackBar = onShowSnackBar)
    }
}