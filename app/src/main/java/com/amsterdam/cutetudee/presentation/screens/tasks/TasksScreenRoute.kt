package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.tasksScreenRoute(navController: NavController){
    composable < Screen.Tasks>{
        TasksScreen(navController = navController)
    }
}