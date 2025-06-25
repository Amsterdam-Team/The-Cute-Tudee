package com.amsterdam.cutetudee.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBar
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarVisuals
import com.amsterdam.cutetudee.presentation.navigation.CuteTudeeBottomNavigation
import com.amsterdam.cutetudee.presentation.navigation.NavGraph
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CuteTudeeApp(
    viewModel: AppViewModel = koinViewModel()
) {
    val isSystemInDarkTheme = viewModel.themeState.collectAsState()
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme.value) {
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = backStackEntry?.destination

        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            Box(Modifier.fillMaxSize()) {
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
                    NavGraph(navController = navController, onShowSnackBar = { message, status ->
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                CustomSnackBarVisuals(
                                    message = message,
                                    status = status,
                                )
                            )
                        }
                    })
                }

                SnackbarHost(
                    hostState = snackBarHostState,
                    snackbar = { snackBarData ->
                        val customVisuals = snackBarData.visuals as? CustomSnackBarVisuals
                        if (customVisuals != null) {
                            CustomSnackBar(customVisuals.message, customVisuals.status)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("NavController not found")
}







