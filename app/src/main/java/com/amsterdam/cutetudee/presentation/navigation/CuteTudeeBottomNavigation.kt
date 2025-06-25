package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun CuteTudeeBottomNavigation(
    currentDestination: NavDestination?,
    onNavigate: (NavigationBarItems) -> Unit,
    modifier: Modifier = Modifier
) {
    val visible = remember(currentDestination) {
        shouldShowBottomNavigation(currentDestination)
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(600),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(600),
            targetOffsetY = { it }
        )
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = AppTheme.color.surfaceHigh,
        ) {
            NavigationBarItems.entries.forEach { item ->
                val isSelected = remember(currentDestination, item) {
                    isDestinationSelected(currentDestination, item)
                }

                BottomNavigationItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigate(item) }
                )
            }
        }
    }
}

private fun isDestinationSelected(
    currentDestination: NavDestination?,
    item: NavigationBarItems
): Boolean {
    return currentDestination?.hierarchy?.any { destination ->
        destination.hasRoute(item.screen::class)
    } == true
}

private fun shouldShowBottomNavigation(currentDestination: NavDestination?): Boolean {
    return NavigationBarItems.entries
        .map { it.screen::class }
        .any { route ->
            currentDestination?.hierarchy?.any { destination ->
                destination.hasRoute(route)
            } == true
        }
}