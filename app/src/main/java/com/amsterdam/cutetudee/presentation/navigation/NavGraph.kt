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

         onBoardingScreenRoute(navController = navController)

         homeScreenRoute(navController = navController)

         tasksScreenRoute(navController = navController)

         categoryScreenRoute(navController = navController)
    }
 }