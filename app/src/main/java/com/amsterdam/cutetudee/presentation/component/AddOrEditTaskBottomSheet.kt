package com.amsterdam.cutetudee.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskInteractionListener
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState.CategoryItemUiState
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dropShadow
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun AddOrEditTaskBottomSheet(
    taskAction: AddEditTaskUiState.TaskAction,
    modifier: Modifier = Modifier,
    interactionListener: AddEditTaskInteractionListener,
    taskName: String,
    taskDescription: String,
    date: String,
    dateInMillis: Long,
    priority: PriorityUi,
    selectedCategoryId: String,
    categories: List<CategoryItemUiState>,
    isLoading: Boolean,
    isEnabled: Boolean
) {
    CustomBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        onDismissRequest = interactionListener::onDismiss
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
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
                        taskName = taskName,
                        onTitleValueChanged = interactionListener::onTaskNameChanged
                    )
                }
                item {
                    DescriptionTextField(
                        Modifier.fillMaxWidth(),
                        description = taskDescription,
                        onDescriptionValueChanged = interactionListener::onTaskDescriptionChanged
                    )
                }
                item {
                    DateTextField(
                        Modifier.fillMaxWidth(),
                        date = date,
                        onDateValueChanged = interactionListener::onDateChanged,
                        dateInMillis = dateInMillis,
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
                        priorityUi = priority,
                        onClick = interactionListener::onPriorityChanged
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
                        categoryItemUiStates = categories,
                        selectedCategoryId = selectedCategoryId,
                        onCategorySelected = interactionListener::onCategorySelected
                    )
                }
            }
            ActionButtons(
                modifier = Modifier.fillMaxWidth(),
                taskAction = taskAction,
                isLoading = isLoading,
                isEnabled = isEnabled,
                onCancel = interactionListener::onCancel,
                onAction = interactionListener::onAction
            )
        }

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalUuidApi::class)
@Composable
private fun CategorySection(
    modifier: Modifier,
    selectedCategoryId: String,
    categoryItemUiStates: List<CategoryItemUiState>,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        val chunks = LocalConfiguration.current.screenWidthDp / 125
        categoryItemUiStates.chunked(chunks).forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                it.forEach { categoryItemUiState ->
                    SelectedBadgedCategory(
                        categoryId = categoryItemUiState.id,
                        categoryName = categoryItemUiState.name,
                        categoryImage = categoryItemUiState.image,
                        isSelected = selectedCategoryId == categoryItemUiState.id,
                        onCategorySelected = { onCategorySelected(it) },
                        modifier = Modifier.weight(1f)
                    )
                }
                (1..(chunks - it.size)).forEach { _ ->
                    Box(modifier = Modifier.weight(1f))
                }
            }
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
        PriorityUi.entries.forEach { priority ->
            PriorityChip(
                priorityUi = priority,
                isSelected = priority.name == priorityUi?.name,
                onclick = { onClick(it) }
            )
        }
    }
}

@Composable
private fun DateTextField(
    modifier: Modifier,
    date: String,
    dateInMillis: Long,
    onDateValueChanged: (date: Long) -> Unit,
) {

    val showDatePicker = remember { mutableStateOf(false) }

    ReadOnlyCustomTextField(
        text = date,
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
                dateInMillis = dateInMillis,
                showDatePicker = showDatePicker,
                onDateValueChanged = onDateValueChanged,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenDatePicker(
    modifier: Modifier,
    date: String,
    dateInMillis: Long,
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
        initialSelectedDateMillis = dateInMillis,
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
        maxCharacters = 300
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
        onValueChange = { onTitleValueChanged(it) },
        maxCharacters = 60
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
@ThemeAndLocalePreviews
@Composable
private fun AddOrEditTaskBottomSheetPreview() {
    CuteTudeeTheme {
        AddOrEditTaskBottomSheet(
            taskAction = AddEditTaskUiState.TaskAction.ADD,
            modifier = Modifier,
            interactionListener = object : AddEditTaskInteractionListener {
                override fun onTaskNameChanged(updatedTaskName: String) {}
                override fun onTaskDescriptionChanged(updatedTaskDescription: String) {}
                override fun onPriorityChanged(priority: Task.Priority) {}
                override fun onDateChanged(date: Long) {}
                override fun onCategorySelected(categoryId: String) {}
                override fun onAction() {}
                override fun onCancel() {}
                override fun onDismiss() {}
            },
            taskName = "Task",
            taskDescription = "Description",
            date = "",
            dateInMillis = 100L,
            priority = PriorityUi.LOW,
            selectedCategoryId = "",
            categories = emptyList(),
            isLoading = false,
            isEnabled = false
        )
    }
}