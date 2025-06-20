package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun OverviewCard(
    countOfTasks: Int,
    tasksState: Task.Status,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .zIndex(999f)
            .height(112.dp)
            .background(backgroundColor, shape = RoundedCornerShape(20.dp))
    ) {
        Column(modifier.padding(12.dp)) {
            Box(
                modifier
                    .size(40.dp)
                    .background(
                        AppTheme.color.surfaceHigh.copy(0.12f), shape = RoundedCornerShape(12.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (tasksState) {
                            Task.Status.TODO -> R.drawable.todo_icon
                            Task.Status.IN_PROGRESS -> R.drawable.in_progress_icon
                            Task.Status.DONE -> R.drawable.done_icon
                        }
                    ),
                    contentDescription = null,
                    tint = AppTheme.color.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = countOfTasks.toString(),
                style = AppTheme.textStyle.headLine.medium,
                color = AppTheme.color.onPrimary
            )
            Text(
                text = when (tasksState) {
                    Task.Status.TODO -> stringResource(R.string.todo)
                    Task.Status.IN_PROGRESS -> stringResource(R.string.in_progress)
                    Task.Status.DONE -> stringResource(R.string.done)
                }, style = AppTheme.textStyle.label.small, color = AppTheme.color.onPrimaryCaption
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun OverviewCard() {
    OverviewCard(
        countOfTasks = 5,
        tasksState = Task.Status.IN_PROGRESS,
        backgroundColor = AppTheme.color.primary,
    )
}