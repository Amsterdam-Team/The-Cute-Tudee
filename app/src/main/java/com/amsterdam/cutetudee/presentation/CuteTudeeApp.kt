package com.amsterdam.cutetudee.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amsterdam.cutetudee.presentation.navigation.CuteTudeeBottomNavigation
import com.amsterdam.cutetudee.presentation.navigation.NavGraph
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CuteTudeeApp() {
    CuteTudeeTheme(isDarkTheme = false) {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = backStackEntry?.destination

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            containerColor = AppTheme.color.surface,
            bottomBar = {
                CuteTudeeBottomNavigation(
                    currentDestination = currentDestination,
                    onNavigate = { destination ->
                        navController.navigate(destination.screen) {
                            launchSingleTop = true
                            popUpTo(Screen.Home) {
                                inclusive = false
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavGraph(navController = navController)
        }
    }
}







