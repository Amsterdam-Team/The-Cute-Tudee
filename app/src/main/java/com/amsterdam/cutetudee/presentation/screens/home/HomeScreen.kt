package com.amsterdam.cutetudee.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.LocalNavController
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsBottomSheet
import com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails.TaskDetailsUiState
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.LoadingIndicator
import com.amsterdam.cutetudee.presentation.component.NoTasksContainer
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_padding.bottomNavigationBarPadding
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskInteractionListener
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import com.amsterdam.cutetudee.presentation.screens.component.AddOrEditTaskBottomSheet
import com.amsterdam.cutetudee.presentation.screens.home.component.OverlayBoxContent
import com.amsterdam.cutetudee.presentation.screens.home.component.TaskSection
import com.amsterdam.cutetudee.presentation.screens.home.component.TopCuteTudeeAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.toStringFormatedDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun HomeScreen(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current
    val state by homeViewModel.homeState.collectAsState()
    val addedTaskSuccessfullyMessage = stringResource(R.string.add_task_success)
    val editedTaskSuccessfullyMessage = stringResource(R.string.edit_task_success)
    val failAddTask = stringResource(R.string.add_task_fail)
    val failEditTask = stringResource(R.string.edit_task_fail)
    val failLoadTask = stringResource(R.string.load_task_fail)
    val failLoadCategory = stringResource(R.string.load_category_fail)

    LaunchedEffect(homeViewModel.homeEffect) {
        homeViewModel.homeEffect.collectLatest {
            when (it) {
                HomeEffect.ShowTaskAddedSuccessfullySnackBar ->
                    onShowSnackBar(
                        addedTaskSuccessfullyMessage,
                        CustomSnackBarStatus.Success,
                    )

                HomeEffect.ShowTaskEditedSuccessfullySnackBar ->
                    onShowSnackBar(
                        editedTaskSuccessfullyMessage,
                        CustomSnackBarStatus.Success,
                    )

                HomeEffect.ShowTaskAddedFailedSnackBar -> onShowSnackBar(
                    failAddTask,
                    CustomSnackBarStatus.Failure,
                )

                HomeEffect.ShowTaskEditedFailedSnackBar -> onShowSnackBar(
                    failEditTask,
                    CustomSnackBarStatus.Failure,
                )

                HomeEffect.ShowFailedToLoadCategoriesSnackBar -> onShowSnackBar(
                    failLoadCategory,
                    CustomSnackBarStatus.Failure,
                )

                HomeEffect.ShowFailedToLoadTaskSnackBar -> onShowSnackBar(
                    failLoadTask,
                    CustomSnackBarStatus.Failure,
                )
            }
        }
    }
    HomeScreenContent(
        state,
        homeInteraction = homeViewModel,
        onNavigateToTaskScreen = {
            navController.navigate(Screen.Tasks(it))
        },
        addEditInteractionListener = homeViewModel,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    homeUiState: HomeUiState,
    homeInteraction: HomeScreenInteraction,
    onNavigateToTaskScreen: (TaskStatusUi) -> Unit,
    addEditInteractionListener: AddEditTaskInteractionListener,
) {
    Box(
        Modifier
            .fillMaxSize()
            .bottomNavigationBarPadding(),
    ) {
        CustomFloatingActionButton(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 12.dp)
                    .zIndex(10f),
            onClick = homeInteraction::onAddTaskClicked,
            isLoading = false,
            iconDrawable = painterResource(id = R.drawable.note_add_icon),
            isEnabled = true,
            iconDescription = stringResource(R.string.add_task),
        )

        if (homeUiState.showEditTaskBottomSheet) {
            ShowEditTaskBottomSheet(
                addEditTaskInteractionListener = addEditInteractionListener,
                homeUiState = homeUiState
            )
        }
        if (homeUiState.showAddTaskBottomSheet) {
            ShowAddTaskBottomSheet(
                addEditTaskInteractionListener = addEditInteractionListener,
                homeUiState = homeUiState
            )
        }
        if (homeUiState.showTaskDetailsBottomSheet) {
            if (homeUiState.selectedTask == null) return
            val state = TaskDetailsUiState(homeUiState.selectedTask, false)
            TaskDetailsBottomSheet(
                taskDetailsState = state,
                onMoveToNextStatus = homeInteraction::onMoveToNextStatus,
                onEditClick = homeInteraction::onEditTaskClicked,
                onDismissRequest = homeInteraction::onDismissTaskDetailsBottomSheet
            )
        }
        if (homeUiState.isLoading) {
            LoadingIndicator(
                modifier =
                    Modifier
                        .zIndex(10f)
                        .align(Alignment.Center),
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                TopCuteTudeeAppBar(
                    title = stringResource(R.string.app_title),
                    description = stringResource(R.string.app_subtitle),
                    isDark = homeUiState.isDarkMode,
                    onSwitchTheme = homeInteraction::onSwitchTheme,
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(bottom = 82.dp),
                    modifier =
                        Modifier
                            .weight(1f)
                            .background(AppTheme.color.surface),
                ) {
                    item {
                        val date = homeUiState.currentDate
                        OverlayBoxContent(
                            currentDate = date.toStringFormatedDate(),
                            numberOfCompletedTask = homeUiState.doneTasksNumber,
                            numberOfInProgressTask = homeUiState.inProgressTasksNumber,
                            numberOfToDoTask = homeUiState.toDoTasksNumber,
                            totalNumberOfTasks = homeUiState.totalTasksNumber,
                            moodState = homeUiState.moodState,
                        )
                    }
                    if (homeUiState.totalTasksNumber > 0) {
                        item {
                            TaskSection(
                                title = stringResource(R.string.in_progress),
                                tasks = homeUiState.inProgressTasks,
                                onNavigateToTaskScreen = {
                                    onNavigateToTaskScreen(TaskStatusUi.IN_PROGRESS)
                                },
                                modifier = Modifier,
                                onTaskClick = homeInteraction::onTaskClicked,
                            )
                        }
                        item {
                            TaskSection(
                                title = stringResource(R.string.todo),
                                tasks = homeUiState.todoTasks,
                                onNavigateToTaskScreen = {
                                    onNavigateToTaskScreen(TaskStatusUi.TODO)
                                },
                                modifier = Modifier,
                                onTaskClick = homeInteraction::onTaskClicked,
                            )
                        }
                        item {
                            TaskSection(
                                title = stringResource(R.string.done),
                                tasks = homeUiState.doneTasks,
                                onNavigateToTaskScreen = {
                                    onNavigateToTaskScreen(TaskStatusUi.DONE)
                                },
                                modifier = Modifier,
                                onTaskClick = homeInteraction::onTaskClicked,
                            )
                        }
                    } else {
                        item {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .padding(top = 48.dp)
                                        .padding(horizontal = 15.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                NoTasksContainer(
                                    primaryMessage = stringResource(R.string.empty_tasks_title),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowEditTaskBottomSheet(
    addEditTaskInteractionListener: AddEditTaskInteractionListener,
    homeUiState: HomeUiState
) {
    AddOrEditTaskBottomSheet(
        taskAction = AddEditTaskUiState.TaskAction.EDIT,
        modifier = Modifier,
        interactionListener = addEditTaskInteractionListener,
        taskName = homeUiState.addEditTaskUiState.taskName,
        taskDescription = homeUiState.addEditTaskUiState.description,
        date = homeUiState.addEditTaskUiState.date,
        dateInMillis = homeUiState.addEditTaskUiState.dateInMillis,
        priority = homeUiState.addEditTaskUiState.priority,
        selectedCategoryId = homeUiState.addEditTaskUiState.selectedCategoryId,
        categories = homeUiState.addEditTaskUiState.categories,
        isLoading = homeUiState.addEditTaskUiState.isLoading,
        isEnabled = homeUiState.addEditTaskUiState.isEnabled,
    )
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ShowAddTaskBottomSheet(
    homeUiState: HomeUiState,
    addEditTaskInteractionListener: AddEditTaskInteractionListener
) {
    AddOrEditTaskBottomSheet(
        taskAction = AddEditTaskUiState.TaskAction.ADD,
        modifier = Modifier,
        interactionListener = addEditTaskInteractionListener,
        taskName = homeUiState.addEditTaskUiState.taskName,
        taskDescription = homeUiState.addEditTaskUiState.description,
        date = homeUiState.addEditTaskUiState.date,
        dateInMillis = homeUiState.addEditTaskUiState.dateInMillis,
        priority = homeUiState.addEditTaskUiState.priority,
        selectedCategoryId = homeUiState.addEditTaskUiState.selectedCategoryId,
        categories = homeUiState.addEditTaskUiState.categories,
        isLoading = homeUiState.addEditTaskUiState.isLoading,
        isEnabled = homeUiState.addEditTaskUiState.isEnabled,
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        HomeUiState(
            isLoading = false,
            currentDate =
                Clock.System
                    .now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date,
            doneTasksNumber = 5,
            inProgressTasksNumber = 3,
            toDoTasksNumber = 8,
            totalTasksNumber = 16,
            doneTasks = emptyList(),
            inProgressTasks = emptyList(),
            todoTasks = emptyList(),
            errorMessageId = null,
            moodState = MoodState.STAY_WORKING,
            isDarkMode = false,
        ),
        homeInteraction =
            object : HomeScreenInteraction {
                override fun onAddTaskClicked() {}

                override fun onDismissAddBottomSheet() {}

                override fun onTaskClicked(taskId: String) {}

                override fun onDismissTaskDetailsBottomSheet() {}
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
                override fun onMoveToNextStatus(taskStatusUi: TaskStatusUi) {
                }

                override fun onSwitchTheme() {}
            },
        onNavigateToTaskScreen = {},
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
