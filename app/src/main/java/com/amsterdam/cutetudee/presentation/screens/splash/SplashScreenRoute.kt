package com.amsterdam.cutetudee.presentation.screens.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.splashScreenRoute() {
    composable<Screen.Splash> {
        SplashScreen()
    }
}