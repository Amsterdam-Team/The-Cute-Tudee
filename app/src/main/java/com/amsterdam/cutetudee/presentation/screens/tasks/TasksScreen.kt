package com.amsterdam.cutetudee.presentation.screens.tasks

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsBottomSheet
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsUiState
import com.amsterdam.cutetudee.presentation.component.CustomDatePickerDialog
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.NoTasksContainer
import com.amsterdam.cutetudee.presentation.component.TabsContent
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.getCurrentMonthDays
import com.amsterdam.cutetudee.presentation.utils.monthDays
import com.amsterdam.cutetudee.presentation.utils.toStringFormatedDate
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin
import java.time.format.TextStyle
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    modifier: Modifier = Modifier,
    dateTimeHandler: IDateTimeHandler = getKoin().get(),
    viewModel: TasksViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val deletedSuccessfullyMessage = stringResource(R.string.delete_task_success)
    Box(
        modifier
            .fillMaxSize()
            .background(AppTheme.color.surfaceHigh)
            .navigationBarsPadding()
            .statusBarsPadding(),
    ) {
        TasksContent(
            tasksUiState = state,
            dateTimeHandler = dateTimeHandler,
            onTabChange = viewModel::filteredTasksByStatus,
            onUpdateSelectedDate = viewModel::getTasksByDate,
            onNavigateToNextMonth = viewModel::navigateToNextMonth,
            onNavigateToPreviousMonth = viewModel::navigateToPreviousMonth,
            onDeleteTask = { task ->
                viewModel.deleteTask(task) {
                    onShowSnackBar(deletedSuccessfullyMessage, CustomSnackBarStatus.Success)
                }
            },
            onMoveTaskToDone = { taskUi, onSuccess -> viewModel.updateTaskStatusToDone(taskUi, onSuccess) },
            showTaskDetails = viewModel::onShowTaskDetails,
            modifier = Modifier,
        )

        CustomFloatingActionButton(
            onClick = viewModel::onFabAction,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 12.dp, vertical = 12.dp),
            isEnabled = true,
            iconDescription = "Add task",
            isLoading = false,
            iconDrawable = painterResource(R.drawable.note_add_icon),
        )

        if (state.showAddTaskBottomSheet) {
            ShowAddTaskBottomSheet(
                viewModel::onDismissFabButton,
            )
        }

        if (state.showTaskDetailsBottomSheet) {
            ShowTaskDetailsBottomSheet(
                state.taskDetails!!,
                onMoveItemToDone = viewModel::updateTaskStatusToDone,
                onDismiss = viewModel::onDismissTaskDetails,
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowAddTaskBottomSheet(onDismiss: () -> Unit = {}) {
    AddOrEditTaskBottomSheet(
        taskAction = AddEditTaskUiState.TaskAction.ADD,
        onDismiss = onDismiss,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksContent(
    tasksUiState: TasksUiState,
    dateTimeHandler: IDateTimeHandler,
    onTabChange: (TaskStatusUi) -> Unit,
    onUpdateSelectedDate: (LocalDate) -> Unit,
    onNavigateToNextMonth: () -> Unit,
    onNavigateToPreviousMonth: () -> Unit,
    onDeleteTask: (TaskUi) -> Unit,
    onMoveTaskToDone: (TaskUi, () -> Unit) -> Unit,
    showTaskDetails: (TaskUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(AppTheme.color.surfaceHigh),
        ) {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                text = stringResource(R.string.tasks),
                style = AppTheme.textStyle.title.large,
                color = AppTheme.color.title,
            )
            DateContainer(
                dateTimeHandler = dateTimeHandler,
                currentSelectedDate = tasksUiState.currentDate,
                onUpdateSelectedDate = onUpdateSelectedDate,
                onNavigateToNextMonth = onNavigateToNextMonth,
                onNavigateToPreviousMonth = onNavigateToPreviousMonth,
            )
            TabsContent(
                selectedStatus = tasksUiState.currentSelectedTaskStatusUi,
                numberOfTasks = tasksUiState.filteredTasks.size,
                onTabChange = onTabChange,
            )
            if (tasksUiState.tasks.isEmpty()) {
                Box(modifier = Modifier.weight(1f)) {
                    NoTasksContainer(
                        primaryMessage = stringResource(R.string.empty_tasks_title),
                        modifier =
                            Modifier
                                .align(Alignment.Center)
                                .padding(start = 10.dp, end = 20.dp),
                    )
                }
            } else {
                TasksContainer(
                    tasks = tasksUiState.filteredTasks,
                    showDetailsBottomSheet = showTaskDetails,
                    onDelete = onDeleteTask,
                    onMoveItemToDone = onMoveTaskToDone,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateContainer(
    dateTimeHandler: IDateTimeHandler,
    currentSelectedDate: LocalDate,
    onUpdateSelectedDate: (LocalDate) -> Unit,
    onNavigateToNextMonth: () -> Unit,
    onNavigateToPreviousMonth: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateText = "${
        currentSelectedDate.month.getDisplayName(
            TextStyle.SHORT,
            Locale.current.platformLocale,
        )
    }, ${currentSelectedDate.year}"
    val dateOfDay: Int = currentSelectedDate.dayOfMonth
    val daysOfMonth: List<Int> = currentSelectedDate.monthDays()
    var currentSelected by remember { mutableIntStateOf(dateOfDay) }
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = currentSelected.dec())
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(AppTheme.color.surfaceHigh)
                .systemBarsPadding(),
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
            var showDatePicker by remember { mutableStateOf(false) }
            ArrowContainer(
                arrowIcon = R.drawable.left_arrow_icon,
                onClick = onNavigateToPreviousMonth,
            )
            DateTextContainer(
                dateText = dateText,
                onOpenDatePicker = {
                    showDatePicker = true
                },
            )
            if (showDatePicker) {
                CustomDatePickerDialog(
                    dateTimeHandler = dateTimeHandler,
                    onDismissRequest = { showDatePicker = false },
                    onDateSelected = { dateInMillis ->
                        val selectedDate = dateTimeHandler.getLocalDateFromMillis(dateInMillis)
                        onUpdateSelectedDate(selectedDate)
                        currentSelected = selectedDate.dayOfMonth
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(currentSelected.dec())
                        }
                    },
                )
            }
            ArrowContainer(
                arrowIcon = R.drawable.right_arrow_icon,
                onClick = onNavigateToNextMonth,
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
                    isClicked = currentSelected == item,
                    onSelect = {
                        currentSelected = item
                        onUpdateSelectedDate(
                            LocalDate(
                                currentSelectedDate.year,
                                currentSelectedDate.month,
                                item,
                            ),
                        )
                    },
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
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onSelect)
                .background(brush = bkColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val dateOfDayColor = if (isClicked) AppTheme.color.onPrimary else AppTheme.color.body
        val dayColor = if (isClicked) AppTheme.color.onPrimaryCaption else AppTheme.color.hint

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
                ).clickable(onClick = onClick),
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

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TasksContainer(
    tasks: List<TaskUi>,
    showDetailsBottomSheet: (TaskUi) -> Unit,
    onMoveItemToDone: (TaskUi, () -> Unit) -> Unit,
    onDelete: (TaskUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.background(AppTheme.color.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(tasks) { task ->
            var showEditBottomSheet by remember { mutableStateOf(false) }
            TaskItemCard(
                categoryImage = task.categoryUi.image,
                showDate = false,
                priorityUi = task.priority,
                title = task.title,
                description = task.description,
                date = task.date.toStringFormatedDate(),
                isDeletable = true,
                onDeleteAction = { onDelete(task) },
                onClick = {
                    showDetailsBottomSheet(task)
                },
            )
            if (showEditBottomSheet) {
                AddOrEditTaskBottomSheet(
                    taskAction = AddEditTaskUiState.TaskAction.EDIT,
                    taskId = task.id,
                    onDismiss = { showEditBottomSheet = false },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
private fun ShowTaskDetailsBottomSheet(
    task: TaskUi,
    onMoveItemToDone: (TaskUi, () -> Unit) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isTaskDone by remember { mutableStateOf(task.status == TaskStatusUi.DONE) }
    var state = TaskDetailsUiState(task, false)
    TaskDetailsBottomSheet(
        taskDetailsState = state,
        onMoveToDoneClick = {
            state = state.copy(isLoading = true)
            onMoveItemToDone(task) {
                isTaskDone = true
                state = state.copy(task.copy(status = TaskStatusUi.DONE), isLoading = false)
            }
        },
        onEditClick = { },
        onDismissRequest = onDismiss,
        modifier = modifier,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@ThemeAndLocalePreviews
@Composable
private fun TaskContentPreview() {
    CuteTudeeTheme {
        TasksContent(
            tasksUiState = TasksUiState(),
            dateTimeHandler = DateTimeHandler(),
            onTabChange = {},
            onUpdateSelectedDate = {},
            onNavigateToNextMonth = {},
            onNavigateToPreviousMonth = {},
            onDeleteTask = {},
            onMoveTaskToDone = { _, _ -> },
            showTaskDetails = {},
        )
    }
}
