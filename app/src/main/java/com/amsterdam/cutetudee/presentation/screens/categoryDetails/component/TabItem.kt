package com.amsterdam.cutetudee.presentation.screens.categoryDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TabItem(
    tab: Tab,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    badgeColor: Color = AppTheme.color.surface,
    indicatorColor: Color = AppTheme.color.secondary,
    indicatorHeight: Dp = 4.dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.Companion.weight(1f),
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = tab.title,
                    style = AppTheme.textStyle.label.medium,
                    color = if (isSelected) selectedTextColor else unselectedTextColor
                )
                if (tab.count > 0 && isSelected) {
                    Box(
                        modifier = Modifier.Companion
                            .clip(CircleShape)
                            .background(badgeColor),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(
                            modifier = Modifier.Companion.padding(
                                horizontal = 4.dp,
                                vertical = 3.dp
                            ),
                            text = tab.count.toString(),
                            style = AppTheme.textStyle.label.medium,
                            color = AppTheme.color.body
                        )
                    }
                }
            }
            if (isSelected)
                Box(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(horizontal = indicatorHeight)
                        .height(4.dp)
                        .background(
                            color = indicatorColor,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                )
        }
    }
}

@Composable
@ThemeAndLocalePreviews
private fun TabItemPreview() {
    TabItem(
        tab = Tab(title = "In progress", count = 14, isSelected = true),
        isSelected = true,
        onClick = { },
        selectedTextColor = AppTheme.color.title,
        unselectedTextColor = AppTheme.color.surface,
    )
}