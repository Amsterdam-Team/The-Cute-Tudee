package com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusChip
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.CategoryUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.imageModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsBottomSheet(
    taskDetailsState: TaskDetailsUiState,
    onMoveToNextStatus: (nextStatus: TaskStatusUi) -> Unit,
    onEditClick: (
        id: String,
        name: String,
        description: String,
        date: LocalDate,
        priority: PriorityUi,
        selectedCategoryId: String
    ) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {
    CustomBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
        ) {
            Text(
                text = stringResource(id = R.string.task_details),
                color = AppTheme.color.title,
                style = AppTheme.textStyle.title.large,
            )

            TaskDetailsSection(
                taskDetailsState = taskDetailsState,
                onMoveToNextStatus = onMoveToNextStatus,
                onEditClick = onEditClick,
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
private fun TaskDetailsSection(
    taskDetailsState: TaskDetailsUiState,
    onMoveToNextStatus: (nextStatus: TaskStatusUi) -> Unit,
    onEditClick: (
        id: String,
        name: String,
        description: String,
        date: LocalDate,
        priority: PriorityUi,
        selectedCategoryId: String
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(top = 12.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(AppTheme.color.surfaceHigh),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageModel(
                        context,
                        taskDetailsState.task.categoryUi.image
                    )
                ),
                contentDescription = stringResource(id = R.string.category_image),
                modifier = Modifier
                    .padding(12.dp),
            )
        }

        with(taskDetailsState) {
            Text(
                text = task.title,
                color = AppTheme.color.title,
                style = AppTheme.textStyle.title.medium,
                modifier = Modifier.padding(top = 8.dp),
            )

            Text(
                text = task.description,
                color = AppTheme.color.body,
                style = AppTheme.textStyle.body.medium,
                modifier = Modifier.padding(top = 8.dp),
            )

            HorizontalDivider(
                color = AppTheme.color.stroke,
                modifier = Modifier.padding(vertical = 12.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TaskStatusChip(
                    taskStatusUi = task.status,
                    modifier = Modifier.animateContentSize(),
                )
                PriorityChip(
                    priorityUi = task.priority,
                    isSelected = true,
                )
            }

            AnimatedVisibility(visible = task.status != TaskStatusUi.DONE) {
                TaskActionsSection(
                    isLoading = isLoading,
                    onMoveToNextStatus = onMoveToNextStatus,
                    onEditClick = {
                        onEditClick(
                            taskDetailsState.task.id.toString(),
                            taskDetailsState.task.title,
                            taskDetailsState.task.description,
                            taskDetailsState.task.date,
                            taskDetailsState.task.priority,
                            taskDetailsState.task.categoryUi.id.toString(),
                        )
                    },
                    currentStatus = task.status
                )
            }
        }
    }
}

@Composable
private fun TaskActionsSection(
    isLoading: Boolean,
    onMoveToNextStatus: (nextStatus: TaskStatusUi) -> Unit,
    onEditClick: () -> Unit,
    currentStatus: TaskStatusUi,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(top = 24.dp),
    ) {
        OutlinedButton(
            onClick = onEditClick,
            border = BorderStroke(1.dp, AppTheme.color.stroke),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
            shape = CircleShape,
            modifier = Modifier.height(56.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_task_icon),
                contentDescription = stringResource(id = R.string.edit_task),
                tint = AppTheme.color.primary,
                modifier = Modifier.size(24.dp),
            )
        }

        val nextStatus by animateIntAsState(
            targetValue = when (currentStatus) {
                TaskStatusUi.IN_PROGRESS -> TaskStatusUi.DONE.ordinal
                TaskStatusUi.TODO -> TaskStatusUi.IN_PROGRESS.ordinal
                TaskStatusUi.DONE -> TaskStatusUi.DONE.ordinal
            },
        )
        val buttonTitle by animateIntAsState(
            targetValue = when (currentStatus) {
                TaskStatusUi.IN_PROGRESS -> R.string.move_to_done
                TaskStatusUi.TODO -> R.string.move_to_in_progress
                TaskStatusUi.DONE -> R.string.done
            }
        )

        OutlineButton(
            text = stringResource(buttonTitle),
            onClick = { onMoveToNextStatus(TaskStatusUi.entries[nextStatus]) },
            isLoading = isLoading,
            modifier = Modifier
                .height(56.dp)
                .weight(1f),
        )
    }
}

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Preview(name = "TaskDetailsBottomSheet")
@Composable
private fun PreviewTaskDetailsBottomSheet() {
    val coroutineScope = rememberCoroutineScope()
    val task =
        TaskUi(
            id = Uuid.random(),
            title = "Organize Study Desk",
            description = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
            date =
                Clock.System
                    .now()
                    .toLocalDateTime(TimeZone.UTC)
                    .date,
            priority = PriorityUi.HIGH,
            status = TaskStatusUi.TODO,
            categoryUi = CategoryUi(
                id = Uuid.random(),
                name = "TODO()",
                image = Uri.EMPTY,
                numberOfTasks = 5,
                isUserCreated = true
            ),
        )

    var mTask by remember { mutableStateOf(task) }
    var mLoading by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

        TaskDetailsBottomSheet(
            taskDetailsState = TaskDetailsUiState(mTask, mLoading),
            onMoveToNextStatus = { nextStatus ->
                coroutineScope.launch {
                    mLoading = true
                    delay(5000L)
                    mLoading = false
                    mTask = task.copy(status = nextStatus)
                }
            },
            onEditClick = { _, _, _, _, _, _ -> },
        )


    }
}