package com.amsterdam.cutetudee.presentation.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.homeScreenRoute(navController: NavController){
    composable<Screen.Home> {
        HomeScreen(navController = navController)

    }
}