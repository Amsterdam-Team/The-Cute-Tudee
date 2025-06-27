package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animation.animateColor

@Composable
fun TabsContent(
    selectedStatus: TaskStatusUi,
    numberOfTasks: Int,
    onTabChange: (TaskStatusUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableIntStateOf(selectedStatus.ordinal) }
    val borderColor = AppTheme.color.stroke
    val tabs = TaskStatusUi.entries

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier =
            modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = borderColor,
                        start = Offset(
                            0f,
                            size.height - strokeWidth / 2
                        ),
                        end = Offset(
                            size.width,
                            size.height - strokeWidth / 2
                        ),
                        strokeWidth = strokeWidth,
                    )
                },
        indicator = @Composable { tabPositions ->
            val currentTabPosition = tabPositions[selectedTabIndex]
            Box(
                modifier =
                    Modifier
                        .tabIndicatorOffset(currentTabPosition)
                        .height(4.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(AppTheme.color.secondary),
            )
        },
        containerColor = AppTheme.color.surfaceHigh,
    ) {
        tabs.forEachIndexed { index, status ->
            val isSelected = selectedTabIndex == index
            val titleColor = animateColor(condition = isSelected, trueColor = AppTheme.color.title, falseColor = AppTheme.color.hint)
            val titleStyle = if (isSelected) AppTheme.textStyle.title.medium else AppTheme.textStyle.title.small
            Tab(
                selected = isSelected,
                onClick = {
                    selectedTabIndex = index
                    onTabChange(status)
                },
            ) {
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = status.labelRes),
                        style = titleStyle,
                        color = titleColor,
                        modifier =
                            Modifier
                                .padding(vertical = 16.dp)
                                .animateContentSize(),
                    )
                    AnimatedVisibility(isSelected) {
                        NotificationBadge(numberOfTasks.toString())
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationBadge(
    badgeCount: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(start = 4.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(AppTheme.color.surface),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = badgeCount,
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body,
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TabsContentPreview() {
    TabsContent(
        selectedStatus = TaskStatusUi.TODO,
        numberOfTasks = 3,
        onTabChange = {},

        )
}