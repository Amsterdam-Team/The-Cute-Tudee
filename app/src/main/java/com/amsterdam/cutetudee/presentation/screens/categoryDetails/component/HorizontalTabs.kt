package com.amsterdam.cutetudee.presentation.screens.categoryDetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

data class Tab(
    val title: String,
    val count: Int = 0,
    val isSelected: Boolean = false
)

@Composable
private fun HorizontalTabs(
    tabs: List<Tab>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    selectedTextColor: Color = AppTheme.color.title,
    unselectedTextColor: Color = AppTheme.color.hint,
    backgroundColor: Color = AppTheme.color.surfaceHigh,
    badgeColor: Color = AppTheme.color.surface,
    indicatorColor: Color = AppTheme.color.secondary,
    indicatorHeight: Dp = 4.dp
) {
    Column(
        modifier = modifier.height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                TabItem(
                    modifier = Modifier.weight(1f),
                    tab = tab,
                    isSelected = index == selectedTabIndex,
                    onClick = { onTabSelected(index) },
                    selectedTextColor = selectedTextColor,
                    unselectedTextColor = unselectedTextColor,
                    badgeColor = badgeColor,
                    indicatorColor = indicatorColor,
                    indicatorHeight = indicatorHeight
                )
            }
        }
        HorizontalDivider(color = AppTheme.color.stroke)
    }

}

@Composable
@ThemeAndLocalePreviews
private fun TaskTrackerScreenPreview() {
    val tabs = listOf(
        Tab(title = "In progress", count = 14, isSelected = true),
        Tab(title = "To Do", count = 5),
        Tab(title = "Done")
    )

    var selectedTabIndex by remember { mutableIntStateOf(0) }


    Surface(color = AppTheme.color.surface) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalTabs(
                modifier = Modifier
                    .height(48.dp),
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                },
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Content for ${tabs[selectedTabIndex].title}",
                    color = AppTheme.color.title
                )
            }
        }

    }
}