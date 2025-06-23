package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.navigation.CuteTudeeBottomNavigation
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.screens.home.component.TopCuteTudeeAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun CuteTudeeScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    backgroundColor: Color = AppTheme.color.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    contentBackground: Color = AppTheme.color.surfaceHigh,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar() },
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        contentColor = contentColor,
        containerColor = backgroundColor,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(contentBackground)
                .padding(innerPadding)
        ) {
            content()
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun CuteTudeeScaffoldPreview() {
    var toggled by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination
    CuteTudeeTheme {
        Surface(
            color = AppTheme.color.surfaceHigh,
        ) {
            CuteTudeeScaffold(floatingActionButton = {
                CustomFloatingActionButton(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 12.dp, end = 12.dp)
                        .zIndex(10f),
                    onClick = {},
                    isLoading = false,
                    iconDrawable = painterResource(id = R.drawable.note_add_icon),
                    isEnabled = true,
                    iconDescription = stringResource(R.string.add_task)
                )
            }, bottomBar = {
                CuteTudeeBottomNavigation(
                    currentDestination = currentDestination, onNavigate = { destination ->
                        navController.navigate(destination.screen) {
                            launchSingleTop = true
                            popUpTo(Screen.Home) {
                                inclusive = false
                            }
                        }
                    })
            }, topBar = {
                TopCuteTudeeAppBar(
                    title = stringResource(R.string.app_title),
                    description = stringResource(R.string.app_subtitle),
                    isDark = toggled,
                    onSwitchTheme = { toggled = !toggled },
                )
            }, content = {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(text = "Hello World")
                }
            })
        }
    }
}