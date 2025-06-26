package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun RowScope.BottomNavigationItem(
    item: NavigationBarItems,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBarItem(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (!isSelected) {
                        onClick()
                    }
                }
            ),
        onClick = {
            if (!isSelected) {
                onClick()
            }
        },
        selected = isSelected,
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
        interactionSource = remember { MutableInteractionSource() }
    )
}