package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.screens.home.HomeUiState.TaskDetails
import com.amsterdam.cutetudee.presentation.utils.toBitmap

@Composable
fun TaskSection(
    title: String,
    tasks: List<TaskDetails>,
    modifier: Modifier = Modifier,
) {
    if (tasks.isEmpty()) return

    Column(
        modifier = modifier,
    ) {
        TextLabelTaskProgress(
            label = title,
            numbersOfItems = tasks.size,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        ) {}

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(266.dp),
        ) {
            items(tasks) { taskItem ->
                TaskItemCard(
                   categoryImage = taskItem.icon,
                    priorityUi = taskItem.taskPriority,
                    title = taskItem.title,
                    description = taskItem.description,
                    modifier =
                        Modifier.width(320.dp),
                )
            }
        }
    }
}
