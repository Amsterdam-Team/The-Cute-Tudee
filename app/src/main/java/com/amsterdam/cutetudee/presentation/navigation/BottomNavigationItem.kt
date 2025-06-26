package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.NoRippleInteractionSource

@Composable
fun RowScope.BottomNavigationItem(
    item: NavigationBarItems,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {  NavigationBarItem(
    modifier = modifier,
    selected = isSelected,
    onClick = {
        if (!isSelected) {
            onClick()
        }
    },
    icon = {
        BottomNavigationIcon(
            item = item,
            isSelected = isSelected
        )
    },
    colors = NavigationBarItemDefaults.colors(
        indicatorColor = AppTheme.color.surfaceHigh,
        selectedIconColor = AppTheme.color.primary,
        unselectedIconColor = AppTheme.color.hint,
    ),
    interactionSource = NoRippleInteractionSource
)}