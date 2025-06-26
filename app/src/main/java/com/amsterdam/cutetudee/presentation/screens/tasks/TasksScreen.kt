package com.amsterdam.cutetudee.presentation.screens.tasks

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsBottomSheet
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsUiState
import com.amsterdam.cutetudee.presentation.component.AddOrEditTaskBottomSheet
import com.amsterdam.cutetudee.presentation.component.ConfirmationBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomDatePickerDialog
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.NoTasksContainer
import com.amsterdam.cutetudee.presentation.component.TabsContent
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_padding.bottomNavigationBarPadding
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskInteractionListener
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animation.animateColor
import com.amsterdam.cutetudee.presentation.utils.getCurrentMonthDays
import com.amsterdam.cutetudee.presentation.utils.monthDays
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import kotlin.uuid.ExperimentalUuidApi

@SuppressLint("SuspiciousIndentation")
@Composable
fun TasksScreen(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    viewModel: TasksViewModel = koinViewModel()
) {
    val state by viewModel.taskUiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is TasksEffect.ShowSuccessDeleteTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.delete_task_success),
                    CustomSnackBarStatus.Success
                )

                is TasksEffect.ShowFailedSnackBar -> onShowSnackBar(
                    context.getString(R.string.error_unknown),
                    CustomSnackBarStatus.Failure
                )

                is TasksEffect.ShowSuccessAddTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.add_task_success),
                    CustomSnackBarStatus.Success
                )

                is TasksEffect.ShowSuccessEditTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.edit_task_success),
                    CustomSnackBarStatus.Success
                )

                is TasksEffect.ShowFailedAddTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.add_task_fail),
                    CustomSnackBarStatus.Failure
                )

                is TasksEffect.ShowFailedEditTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.edit_task_fail),
                    CustomSnackBarStatus.Failure
                )

                TasksEffect.ShowFailedWrongDateTaskSnackBar -> onShowSnackBar(
                    context.getString(R.string.wrong_selected_date_error),
                    CustomSnackBarStatus.Failure
                )
            }
        }
    }

    TasksContent(
        tasksUiState = state,
        tasksInteraction = viewModel,
        addEditInteractionListener = viewModel
    )

}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalUuidApi::class)
@Composable
fun TasksContent(
    tasksUiState: TasksUiState,
    tasksInteraction: TasksInteraction,
    addEditInteractionListener: AddEditTaskInteractionListener,
) {

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .bottomNavigationBarPadding()
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
        ) {
            item {
                Row {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(AppTheme.color.surfaceHigh)
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                        text = stringResource(R.string.tasks),
                        style = AppTheme.textStyle.title.large,
                        color = AppTheme.color.title,
                    )
                }

                DateContainer(
                    currentSelectedDate = tasksUiState.currentDate,
                    onUpdateSelectedDate = tasksInteraction::onUpdateSelectedDate,
                    onSelectedDayChange = tasksInteraction::onSelectedDayChange,
                    onNavigateToNextMonth = tasksInteraction::onNextMonthClicked,
                    onNavigateToPreviousMonth = tasksInteraction::onPreviousMonthClicked,
                    onClickDateDialogButton = tasksInteraction::onClickDateDialogButton,
                    onDismissDateDialogButton = tasksInteraction::onDismissDateDialogButton,
                    isDateDialogVisible = tasksUiState.isDateDialogVisible
                )
                TabsContent(
                    selectedStatus = tasksUiState.currentSelectedTaskStatusUi,
                    numberOfTasks = tasksUiState.tasks.size,
                    onTabChange = tasksInteraction::onTabChange,
                )
            }
            if (tasksUiState.tasks.isEmpty()) {

                item {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .fillMaxWidth()
                    ) {
                        val availableHeight = maxHeight
                        Column(
                            modifier = Modifier
                                .height(availableHeight)
                                .fillMaxWidth()
                                .padding(top = 120.dp)
                        ) {
                            NoTasksContainer(
                                primaryMessage = stringResource(R.string.empty_tasks_title),
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 20.dp),
                            )
                        }
                    }
                }
            } else {
                items(items = tasksUiState.tasks, key = { it.id }) {
                    TasksContainer(
                        task = it,
                        onDelete = tasksInteraction::onDeleteTaskClicked,
                        onTaskClicked = tasksInteraction::onTaskClicked,
                    )
                }
            }
        }
        CustomFloatingActionButton(
            onClick = tasksInteraction::onFabButtonClicked,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
            isEnabled = true,
            iconDescription = "Add task",
            isLoading = false,
            iconDrawable = painterResource(R.drawable.note_add_icon),
        )
    }

    AnimatedVisibility(tasksUiState.isAddTaskBottomSheetVisible) {
        AddOrEditTaskBottomSheet(
            taskAction = AddEditTaskUiState.TaskAction.ADD,
            modifier = Modifier,
            interactionListener = addEditInteractionListener,
            taskName = tasksUiState.addEditTaskUiState.taskName,
            taskDescription = tasksUiState.addEditTaskUiState.description,
            date = tasksUiState.addEditTaskUiState.date,
            dateInMillis = tasksUiState.addEditTaskUiState.dateInMillis,
            priority = tasksUiState.addEditTaskUiState.priority,
            selectedCategoryId = tasksUiState.addEditTaskUiState.selectedCategoryId,
            categories = tasksUiState.addEditTaskUiState.categories,
            isLoading = tasksUiState.addEditTaskUiState.isLoading,
            isEnabled = tasksUiState.addEditTaskUiState.isEnabled,
        )
    }

    AnimatedVisibility(tasksUiState.isDetailsBottomSheetVisible) {
        var state = TaskDetailsUiState(tasksUiState.selectedTask!!, false)
        TaskDetailsBottomSheet(
            taskDetailsState = state,
            onMoveToNextStatus = tasksInteraction::onMoveToNextStatus,
            onEditClick = tasksInteraction::onEditTaskClicked,
            onDismissRequest = tasksInteraction::onDismissDetailsBottomSheet
        )
    }

    AnimatedVisibility(tasksUiState.isEditBottomSheetVisible) {
        AddOrEditTaskBottomSheet(
            taskAction = AddEditTaskUiState.TaskAction.EDIT,
            modifier = Modifier,
            interactionListener = addEditInteractionListener,
            taskName = tasksUiState.addEditTaskUiState.taskName,
            taskDescription = tasksUiState.addEditTaskUiState.description,
            date = tasksUiState.addEditTaskUiState.date,
            dateInMillis = tasksUiState.addEditTaskUiState.dateInMillis,
            priority = tasksUiState.addEditTaskUiState.priority,
            selectedCategoryId = tasksUiState.addEditTaskUiState.selectedCategoryId,
            categories = tasksUiState.addEditTaskUiState.categories,
            isLoading = tasksUiState.addEditTaskUiState.isLoading,
            isEnabled = tasksUiState.addEditTaskUiState.isEnabled,
        )
    }

    ConfirmationBottomSheet(
        isVisible = tasksUiState.isDeleteBottomSheetVisible,
        onAction = tasksInteraction::onConfirmDeletedTheTask,
        onDismiss = tasksInteraction::onDismissDeleteBottomSheet,
        onCancel = tasksInteraction::onDismissDeleteBottomSheet
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateContainer(
    onUpdateSelectedDate: (Long) -> Unit,
    onSelectedDayChange: (Int) -> Unit,
    onNavigateToNextMonth: () -> Unit,
    onNavigateToPreviousMonth: () -> Unit,
    onClickDateDialogButton: () -> Unit,
    onDismissDateDialogButton: () -> Unit,
    isDateDialogVisible: Boolean,
    currentSelectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    val dateText = "${
        currentSelectedDate.month.getDisplayName(
            TextStyle.SHORT,
            Locale.current.platformLocale,
        )
    }, ${currentSelectedDate.year}"
    val dateOfDay: Int = currentSelectedDate.dayOfMonth
    val daysOfMonth: List<Int> = currentSelectedDate.monthDays()
    val lazyListState = rememberLazyListState()
    val layoutDirection = LocalLayoutDirection.current



    LaunchedEffect(Unit) {
        delay(150)
        lazyListState.animateScrollToItem(dateOfDay.dec())
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(AppTheme.color.surfaceHigh),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArrowContainer(
                arrowIcon = R.drawable.left_arrow_icon,
                onClick =
                    if (layoutDirection == LayoutDirection.Rtl)
                        onNavigateToNextMonth
                    else onNavigateToPreviousMonth
            )
            DateTextContainer(
                dateText = dateText,
                onOpenDatePicker = onClickDateDialogButton,
            )
            AnimatedVisibility(isDateDialogVisible) {
                CustomDatePickerDialog(
                    onDismissRequest = onDismissDateDialogButton,
                    onDateSelected = { dateInMillis ->
                        onUpdateSelectedDate(dateInMillis)
                    },
                )
            }
            ArrowContainer(
                arrowIcon = R.drawable.right_arrow_icon,
                onClick = if (layoutDirection == LayoutDirection.Rtl)
                    onNavigateToPreviousMonth
                else onNavigateToNextMonth,
            )
        }

        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(daysOfMonth) { item ->
                DayContainer(
                    dateOfDay = item,
                    day = currentSelectedDate.getCurrentMonthDays(day = item),
                    isClicked = dateOfDay == item,
                    onSelect = { onSelectedDayChange(item) }
                )
            }
        }
    }
}

@Composable
private fun DayContainer(
    dateOfDay: Int,
    day: String,
    isClicked: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gradientColor =
        Brush.verticalGradient(
            listOf(
                AppTheme.color.primaryGradientStart,
                AppTheme.color.primaryGradientEnd,
            ),
        )
    val bkColor: Brush =
        if (isClicked) {
            gradientColor
        } else {
            Brush.verticalGradient(listOf(AppTheme.color.surface, AppTheme.color.surface))
        }
    Column(
        modifier =
            modifier
                .size(56.dp, 65.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onSelect)
                .background(brush = bkColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        val dateOfDayColor = animateColor(
            condition = isClicked,
            trueColor = AppTheme.color.onPrimary,
            falseColor = AppTheme.color.body
        )

        val dayColor = animateColor(
            condition = isClicked,
            trueColor = AppTheme.color.onPrimaryCaption,
            falseColor = AppTheme.color.hint
        )

        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            text = "$dateOfDay",
            style = AppTheme.textStyle.title.medium,
            color = dateOfDayColor,
        )
        Text(
            text = day,
            style = AppTheme.textStyle.body.small,
            color = dayColor,
        )
    }
}

@Composable
private fun DateTextContainer(
    dateText: String,
    onOpenDatePicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onOpenDatePicker),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = dateText,
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body,
        )
        Icon(
            modifier = Modifier.rotate(270f),
            painter = painterResource(R.drawable.left_arrow_icon),
            contentDescription = null,
            tint = AppTheme.color.body,
        )
    }
}

@Composable
private fun ArrowContainer(
    @DrawableRes arrowIcon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .size(32.dp)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = AppTheme.color.stroke,
                )
                .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(arrowIcon),
            contentDescription = null,
            tint = AppTheme.color.body,
            modifier = Modifier.size(20.dp),
        )
    }
}


@OptIn(ExperimentalUuidApi::class)
@Composable
private fun TasksContainer(
    task: TaskUi,
    onDelete: (TaskUi) -> Unit,
    modifier: Modifier = Modifier,
    onTaskClicked: (TaskUi) -> Unit,
) {
    Box(
        modifier = modifier
            .background(AppTheme.color.surface)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {

        TaskItemCard(
            categoryImage = task.categoryUi.image,
            priorityUi = task.priority,
            title = task.title,
            description = task.description,
            isDeletable = true,
            onDeleteAction = { onDelete(task) },
            onClick = {
                onTaskClicked(task)
            }
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@ThemeAndLocalePreviews
@Composable
private fun TaskContentPreview() {
    CuteTudeeTheme {
        TasksContent(
            tasksUiState = TasksUiState(),
            tasksInteraction = object : TasksInteraction {
                override fun onFabButtonClicked() {}
                override fun onDismissFabButton() {}
                override fun onTabChange(taskStatusUi: TaskStatusUi) {}
                override fun onUpdateSelectedDate(dateInMillis: Long) {}
                override fun onNextMonthClicked() {}
                override fun onPreviousMonthClicked() {}
                override fun onClickDateDialogButton() {}
                override fun onDismissDateDialogButton() {}
                override fun onDeleteTaskClicked(taskUi: TaskUi) {}
                override fun onConfirmDeletedTheTask() {}
                override fun onDismissDeleteBottomSheet() {}
                override fun onMoveToNextStatus(taskStatusUi: TaskStatusUi) {}
                override fun onSelectedDayChange(dayNumber: Int) {}
                override fun onTaskClicked(task: TaskUi) {}
                override fun onDismissDetailsBottomSheet() {}
                override fun onEditTaskClicked(
                    id: String,
                    name: String,
                    description: String,
                    date: String,
                    priority: PriorityUi,
                    selectedCategoryId: String
                ) {
                }

                override fun onDismissEditBottomSheet() {}
            },
            addEditInteractionListener = object : AddEditTaskInteractionListener {
                override fun onTaskNameChanged(updatedTaskName: String) {}
                override fun onTaskDescriptionChanged(updatedTaskDescription: String) {}
                override fun onPriorityChanged(priority: Task.Priority) {}
                override fun onDateChanged(date: Long) {}
                override fun onCategorySelected(categoryId: String) {}
                override fun onAction() {}
                override fun onCancel() {}
                override fun onDismiss() {}
            }
        )
    }
}
