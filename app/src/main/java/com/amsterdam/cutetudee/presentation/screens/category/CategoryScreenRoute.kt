package com.amsterdam.cutetudee.presentation.screens.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.categoryScreenRoute(navController: NavController){
    composable<Screen.Categories>{
        CategoryScreen(navController = navController)
    }
}