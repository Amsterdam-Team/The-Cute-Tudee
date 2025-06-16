package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amsterdam.cutetudee.presentation.screens.category.categoryScreenRoute
import com.amsterdam.cutetudee.presentation.screens.home.homeScreenRoute
import com.amsterdam.cutetudee.presentation.screens.onBoarding.onBoardingScreenRoute
import com.amsterdam.cutetudee.presentation.screens.tasks.tasksScreenRoute

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.OnBoarding) {

        // ONBOARDING SCREEN
        onBoardingScreenRoute(navController = navController)

        // HOME SCREEN
        homeScreenRoute(navController = navController)

        // TASKS SCREEN
        tasksScreenRoute(navController = navController)

        // CATEGORY SCREEN
        categoryScreenRoute(navController = navController)
    }
 }