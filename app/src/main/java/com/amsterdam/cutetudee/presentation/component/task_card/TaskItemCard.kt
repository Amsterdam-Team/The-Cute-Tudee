package com.amsterdam.cutetudee.presentation.component.task_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.chip.DateChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TaskItemCard(
    taskItemUiState: TaskItemUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = AppTheme.color.surfaceHigh,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                start = 4.dp, top = 4.dp, end = 12.dp, bottom = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        TaskItemHeader(
            taskItemUiState = taskItemUiState,
            showDate = taskItemUiState.date.isNotEmpty()
        )
        TaskItemInfo(
            taskItemUiState = taskItemUiState,
            showDescription = taskItemUiState.description.isNotEmpty()
        )

    }
}

@Composable
private fun TaskItemHeader(
    taskItemUiState: TaskItemUiState,
    showDate: Boolean,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = taskItemUiState.categoryImage,
                contentDescription = null,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(Modifier.weight(1f))
        AnimatedVisibility(showDate) {
            DateChip(taskItemUiState.date)
        }
        PriorityChip(
            priorityUi = taskItemUiState.priorityUi,
            isSelected = true
        )
    }
}

@Composable
private fun TaskItemInfo(
    taskItemUiState: TaskItemUiState,
    showDescription: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = taskItemUiState.name,
            color = AppTheme.color.body,
            style = AppTheme.textStyle.label.large
        )
        AnimatedVisibility(showDescription) {
            Text(
                text = taskItemUiState.description,
                color = AppTheme.color.hint,
                style = AppTheme.textStyle.label.small,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    TaskItemCard(
        taskItemUiState = TaskItemUiState(
            categoryImage = painterResource(R.drawable.developer_icon),
            priorityUi = PriorityUi.MEDIUM,
            name = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow",
            date = "12-03-2025",
        ),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}