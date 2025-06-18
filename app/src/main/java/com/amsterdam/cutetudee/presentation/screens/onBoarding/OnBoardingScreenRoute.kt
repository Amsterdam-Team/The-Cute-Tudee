package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.onBoardingScreenRoute(navController: NavController){
    composable<Screen.OnBoarding> {
        OnBoardingScreen(navController = navController)
    }
}