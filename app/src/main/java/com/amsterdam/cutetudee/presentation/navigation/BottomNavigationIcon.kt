package com.amsterdam.cutetudee.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme


@Composable
fun BottomNavigationIcon(
    item: NavigationBarItems,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) {
                    AppTheme.color.primaryVariant
                } else {
                    AppTheme.color.surfaceHigh
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = if (isSelected) {
                    item.selectedIcon
                } else {
                    item.unSelectedIcon
                }
            ),
            contentDescription = item.name,
        )
    }
}