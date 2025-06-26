package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.screens.home.HomeUiState.TaskDetails

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskSection(
    title: String,
    tasks: List<TaskDetails>,
    onTaskClick: (String) -> Unit,
    onNavigateToTaskScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (tasks.isEmpty()) return

    Column(
        modifier = modifier,
    ) {
        TextLabelTaskProgress(
            label = title,
            numbersOfItems = tasks.size,
            onClick = onNavigateToTaskScreen,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        )

        FlowColumn(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 16.dp),
            maxItemsInEachColumn = 2,
        ) {
            tasks.forEach { taskItem ->
                TaskItemCard(
                    categoryImage = taskItem.icon,
                    priorityUi = taskItem.taskPriority,
                    title = taskItem.title,
                    description = taskItem.description,
                    modifier =
                        Modifier.width((LocalConfiguration.current.screenWidthDp - 40).dp),
                    onClick = { onTaskClick(taskItem.id) },
                )
            }
        }
    }
}
