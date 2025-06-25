package com.amsterdam.cutetudee.presentation.screens.tasks

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomDatePickerDialog
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.component.ReadOnlyCustomTextField
import com.amsterdam.cutetudee.presentation.component.SelectedBadgedCategory
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dropShadow
import com.amsterdam.cutetudee.presentation.utils.getDateInMillisFromLocalDate
import com.amsterdam.cutetudee.presentation.utils.getStringDateFromLocalDate
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@SuppressLint("RememberReturnType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOrEditTaskBottomSheet(
    taskAction: AddEditTaskUiState.TaskAction,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    taskId: Uuid? = null,
    viewModel: AddEditTaskViewModel = koinViewModel()
) {

    remember {
        if (taskAction == AddEditTaskUiState.TaskAction.EDIT && taskId != null) {
            viewModel.loadTask(taskId, taskAction)
        }
    }

    val addEditTaskUiState = viewModel.state.collectAsState().value

    AddOrEditTaskBottomSheetContent(
        modifier,
        onCancel = onDismiss,
        addEditTaskUiState,
        { taskName -> viewModel.onTaskNameChanged(taskName) },
        { description -> viewModel.onTaskDescriptionChanged(description) },
        { date: Long -> viewModel.onDateChanged(date) },
        { viewModel.onAction() },
        onCategorySelected = { categoryId -> viewModel.onCategorySelected(categoryId) },
        taskAction = taskAction,
        onPrioritySelected = { priority -> viewModel.onPriorityChanged(priority) },
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
    onAction: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onPrioritySelected: (Task.Priority) -> Unit,
    taskAction: AddEditTaskUiState.TaskAction
) {
    CustomBottomSheet(
        modifier = Modifier
            .fillMaxHeight(),
        onDismissRequest = { onCancel() }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Label(
                        text = when (taskAction) {
                            AddEditTaskUiState.TaskAction.ADD -> stringResource(R.string.add_task)
                            AddEditTaskUiState.TaskAction.EDIT -> stringResource(R.string.edit_task)

                        }
                    )
                }
                item {
                    TaskNameTextField(
                        Modifier.fillMaxWidth(),
                        taskName = addEditTaskUiState.taskName,
                        onTitleValueChanged = { onTaskNameValueChanged(it) }
                    )
                }
                item {
                    DescriptionTextField(
                        Modifier.fillMaxWidth(),
                        description = addEditTaskUiState.description,
                        onDescriptionValueChanged = { onDescriptionValueChanged(it) }
                    )
                }
                item {
                    DateTextField(
                        Modifier.fillMaxWidth(),
                        date = addEditTaskUiState.date,
                        onDateValueChanged = onDateValueChanged
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
                        priorityUi = addEditTaskUiState.priority,
                        onClick = { priority -> onPrioritySelected(priority) }
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
                        categoryItemUiStates = addEditTaskUiState.categories,
                        selectedCategoryId = addEditTaskUiState.selectedCategoryId,
                        onCategorySelected = onCategorySelected
                    )
                }
            }
            ActionButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                taskAction = addEditTaskUiState.taskAction,
                isLoading = addEditTaskUiState.isLoading,
                isEnabled = addEditTaskUiState.isDateFilled,
                onCancel = { onCancel() },
                onAction = { onAction() }
            )
        }

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalUuidApi::class)
@Composable
private fun CategorySection(
    modifier: Modifier,
    selectedCategoryId: String,
    categoryItemUiStates: List<AddEditTaskUiState.CategoryItemUiState>,
    onCategorySelected: (String) -> Unit
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(29.dp),
        verticalArrangement = Arrangement.spacedBy(29.dp)
    ) {
        categoryItemUiStates.forEach { categoryItemUiState ->
            SelectedBadgedCategory(
                categoryId = categoryItemUiState.id,
                categoryName = categoryItemUiState.name,
                categoryImage = categoryItemUiState.image,
                isSelected = selectedCategoryId == categoryItemUiState.id,
                onCategorySelected = { onCategorySelected(it) }
            )
        }
    }
}

@Composable
private fun PrioritySection(
    modifier: Modifier,
    priorityUi: PriorityUi?,
    onClick: (Task.Priority) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Task.Priority.entries.forEach { priority ->
            PriorityChip(
                priorityUi = PriorityUi.valueOf(priority.name),
                isSelected = priority.name == priorityUi?.name,
                onclick = { it -> onClick(it) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateTextField(
    modifier: Modifier,
    date: LocalDate,
    onDateValueChanged: (date: Long) -> Unit
) {

    val showDatePicker = remember { mutableStateOf(false) }

    ReadOnlyCustomTextField(
        text = date.getStringDateFromLocalDate(),
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
                date = date,
                showDatePicker = showDatePicker,
                onDateValueChanged = onDateValueChanged
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenDatePicker(
    modifier: Modifier,
    date: LocalDate,
    onDateValueChanged: (date: Long) -> Unit,
    showDatePicker: MutableState<Boolean>
) {
    CustomDatePickerDialog(
        onDismissRequest = { showDatePicker.value = false },
        onDateSelected = {
            onDateValueChanged(it)
            showDatePicker.value = false
        },
        modifier = modifier,
        initialSelectedDateMillis = date.getDateInMillisFromLocalDate(),
    )
}


@Composable
private fun DescriptionTextField(
    modifier: Modifier,
    description: String,
    onDescriptionValueChanged: (description: String) -> Unit
) {
    CustomTextField(
        text = description,
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
    onTitleValueChanged: (taskName: String) -> Unit
) {
    CustomTextField(
        text = taskName,
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
    onAction: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean
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
            onClick = {
                onAction()
                onCancel()
            },
            isLoading = isLoading,
            isNegative = false,
            isEnabled = isEnabled,
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

@OptIn(ExperimentalUuidApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@ThemeAndLocalePreviews
@Composable
private fun AddOrEditTaskBottomSheetPreview() {
    CuteTudeeTheme {
        AddOrEditTaskBottomSheet(
            taskId = Uuid.random(),
            taskAction = AddEditTaskUiState.TaskAction.ADD
        )
    }
}