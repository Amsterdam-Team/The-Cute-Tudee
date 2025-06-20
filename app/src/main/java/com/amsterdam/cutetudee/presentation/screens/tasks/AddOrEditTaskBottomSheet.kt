package com.amsterdam.cutetudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.BadgedCategoryItem
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomDatePickerDialog
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.component.ReadOnlyCustomTextField
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.screens.category.CategoryItemUiState
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dropShadow
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOrEditTaskBottomSheet(
    addEditTaskUiState: AddEditTaskUiState,
    onTaskNameValueChanged: (taskName: String) -> Unit,
    onDescriptionValueChanged: (description: String) -> Unit,
    onDateValueChanged: (date: Long) -> Unit,
    onAction: () -> Unit,
    onCancel: () -> Unit,
    dateTimeHandler: IDateTimeHandler,
    modifier: Modifier = Modifier,
) {
    AddOrEditTaskBottomSheetContent(
        modifier,
        onCancel,
        addEditTaskUiState,
        onTaskNameValueChanged,
        onDescriptionValueChanged,
        onDateValueChanged,
        dateTimeHandler,
        onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AddOrEditTaskBottomSheetContent(
    modifier: Modifier,
    onCancel: () -> Unit,
    addEditTaskUiState: AddEditTaskUiState,
    onTaskNameValueChanged: (taskName: String) -> Unit,
    onDescriptionValueChanged: (description: String) -> Unit,
    onDateValueChanged: (date: Long) -> Unit,
    dateTimeHandler: IDateTimeHandler,
    onAction: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        CustomBottomSheet(
            modifier = Modifier
                .fillMaxHeight(),
            onDismissRequest = { onCancel() }
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Label(
                        text = when (addEditTaskUiState.taskAction) {
                            AddEditTaskUiState.TaskAction.ADD -> stringResource(R.string.add_task)
                            AddEditTaskUiState.TaskAction.EDIT -> stringResource(R.string.edit_task)

                        }
                    )
                }
                item {
                    TaskNameTextField(
                        Modifier.fillMaxWidth(),
                        taskName = addEditTaskUiState.taskName,
                        taskAction = addEditTaskUiState.taskAction,
                        onTitleValueChanged = onTaskNameValueChanged
                    )
                }
                item {
                    DescriptionTextField(
                        Modifier.fillMaxWidth(),
                        taskAction = addEditTaskUiState.taskAction,
                        description = addEditTaskUiState.description,
                        onDescriptionValueChanged = onDescriptionValueChanged
                    )
                }
                item {
                    DateTextField(
                        Modifier.fillMaxWidth(),
                        date = addEditTaskUiState.date,
                        onDateValueChanged = onDateValueChanged,
                        dateTimeHandler = dateTimeHandler
                    )
                }

                item {
                    Label(
                        text = stringResource(R.string.priority),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                item {
                    PrioritySection(
                        modifier = Modifier.fillMaxWidth(),
                        priorityUi = addEditTaskUiState.priority
                    )
                }
                item {
                    Label(
                        text = stringResource(R.string.category),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    CategorySection(
                        modifier = Modifier.fillMaxWidth(),
                        categoryItemUiStates = addEditTaskUiState.categories
                    )
                }
            }
        }
        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            taskAction = addEditTaskUiState.taskAction,
            onCancel = onCancel,
            onAction = onAction
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySection(
    modifier: Modifier,
    categoryItemUiStates: List<CategoryItemUiState>
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(29.dp),
        verticalArrangement = Arrangement.spacedBy(29.dp)
    ) {
        categoryItemUiStates.forEach { categoryItemUiState ->
            BadgedCategoryItem(categoryItemUiState = categoryItemUiState)
        }
    }
}

@Composable
private fun PrioritySection(
    modifier: Modifier,
    priorityUi: PriorityUi
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Task.Priority.entries.forEach { priority ->
            PriorityChip(
                priorityUi = priorityUi,
                isSelected = priority.name == priorityUi.name
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateTextField(
    modifier: Modifier,
    date: LocalDate,
    onDateValueChanged: (date: Long) -> Unit,
    dateTimeHandler: IDateTimeHandler
) {

    val showDatePicker = remember { mutableStateOf(false) }

    ReadOnlyCustomTextField(
        text = dateTimeHandler.getStringDateFromLocalDate(date),
        modifier = modifier.clickable {
            showDatePicker.value = true
        },
        style = AppTheme.textStyle.label.medium,
        maxLines = 1,
        leadingIcon = R.drawable.calendar_add_icon,
        borderColor = AppTheme.color.stroke,
        onClick = {
            showDatePicker.value = true
        },
    )

    showDatePicker.value.let {
        if (it) {
            OpenDatePicker(
                modifier = modifier,
                dateTimeHandler = dateTimeHandler,
                date = date,
                onDateValueChanged = onDateValueChanged
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenDatePicker(
    modifier: Modifier,
    dateTimeHandler: IDateTimeHandler,
    date: LocalDate,
    onDateValueChanged: (date: Long) -> Unit
) {
    CustomDatePickerDialog(
        dateTimeHandler = dateTimeHandler,
        onDismissRequest = {},
        onDateSelected = { onDateValueChanged(it) },
        modifier = modifier,
        initialSelectedDateMillis = date.toEpochDays() * 24 * 60 * 60 * 1000L,
    )
}


@Composable
private fun DescriptionTextField(
    modifier: Modifier,
    taskAction: AddEditTaskUiState.TaskAction,
    description: String,
    onDescriptionValueChanged: (description: String) -> Unit
) {
    CustomTextField(
        text = if (taskAction == AddEditTaskUiState.TaskAction.ADD) {
            description
        } else "",
        modifier = modifier,
        hintText = stringResource(R.string.task_description_hint),
        maxLines = 5,
        borderColor = AppTheme.color.stroke,
        borderFocusedColor = AppTheme.color.primary,
        style = AppTheme.textStyle.label.medium,
        onValueChange = { onDescriptionValueChanged(it) },
    )
}

@Composable
private fun TaskNameTextField(
    modifier: Modifier,
    taskName: String,
    taskAction: AddEditTaskUiState.TaskAction,
    onTitleValueChanged: (taskName: String) -> Unit
) {
    CustomTextField(
        text = if (taskAction == AddEditTaskUiState.TaskAction.ADD) {
            taskName
        } else "",
        modifier = modifier,
        style = AppTheme.textStyle.label.medium,
        hintText = stringResource(R.string.task_title_hint),
        maxLines = 1,
        leadingIcon = R.drawable.note_icon,
        borderColor = AppTheme.color.stroke,
        borderFocusedColor = AppTheme.color.primary,
        onValueChange = { onTitleValueChanged(it) }
    )
}

@Composable
private fun ActionButtons(
    modifier: Modifier,
    taskAction: AddEditTaskUiState.TaskAction,
    onCancel: () -> Unit,
    onAction: () -> Unit
) {
    Column(
        modifier = modifier
            .dropShadow(
                shape = RectangleShape,
                color = AppTheme.color.dropShadowColor,
            )
            .background(AppTheme.color.surfaceHigh),
    ) {
        GradientFilledButton(
            title = stringResource(
                if (taskAction == AddEditTaskUiState.TaskAction.ADD) {
                    R.string.add
                } else R.string.save
            ),
            onClick = { onAction() },
            isLoading = false,
            isNegative = false,
            isEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            paddingValues = PaddingValues(horizontal = 16.dp, vertical = 18.5.dp)
        )

        OutlineButton(
            text = stringResource(R.string.cancel),
            onClick = { onCancel() },
            isLoading = false,
            isEnabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp, bottom = 12.dp),
            textButtonPadding = PaddingValues(vertical = 18.5.dp)
        )
    }
}

@Composable
private fun Label(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = AppTheme.textStyle.title.large,
        color = AppTheme.color.title,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@ThemeAndLocalePreviews
@Composable
private fun AddOrEditTaskBottomSheetPreview() {
    CuteTudeeTheme {
        AddOrEditTaskBottomSheet(
            addEditTaskUiState = AddEditTaskUiState(
                taskName = "title",
                description = "description",
                date = LocalDate.fromEpochDays(1),
                priority = PriorityUi.LOW,
                selectedCategoryId = 0,
                categories = emptyList(),
                taskAction = AddEditTaskUiState.TaskAction.ADD
            ),
            onAction = { },
            onCancel = { },
            onTaskNameValueChanged = {},
            onDescriptionValueChanged = {},
            onDateValueChanged = {},
            dateTimeHandler = DateTimeHandler(),
        )
    }
}