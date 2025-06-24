package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.screens.home.HomeUiState.TaskDetails

@Composable
fun TaskSection(
    title: String,
    tasks: List<TaskDetails>,
    onNavigateToTaskScreen: () -> Unit,
) {
    if (tasks.isEmpty()) return

    TextLabelTaskProgress(
        label = title,
        numbersOfItems = tasks.size,
        onClick = onNavigateToTaskScreen,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(tasks) { taskItem ->
            Column(modifier = Modifier.fillMaxWidth()) {
                TaskItemCard(
                    modifier =
                        Modifier
                            .fillParentMaxWidth(0.95f)
                            .padding(bottom = 8.dp),
                    categoryImage = taskItem.icon,
                    priorityUi = taskItem.taskPriority,
                    title = taskItem.title,
                    description = taskItem.description,
                )
            }
        }
    }
}
