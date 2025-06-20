package com.amsterdam.cutetudee.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.LoadingIndicator
import com.amsterdam.cutetudee.presentation.component.NoTasksContainer
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.home.component.OverlayBoxContent
import com.amsterdam.cutetudee.presentation.screens.home.component.TaskSection
import com.amsterdam.cutetudee.presentation.screens.home.component.TopCuteTudeeAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.bottomNavigationBarPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit = { _, _ -> },
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val state = homeViewModel.homeState.collectAsState()
    HomeScreenContent(state.value, homeViewModel::onToggledAction)
    if (state.value.errorMessageId != null)
        onShowSnackBar(stringResource(state.value.errorMessageId!!), CustomSnackBarStatus.Failure)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(homeUiState: HomeUiState, onSwitchTheme: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .bottomNavigationBarPadding()
    ) {
        CustomFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 12.dp, end = 12.dp)
                .zIndex(10f),
            onClick = { /*TODO*/ },
            isLoading = false,
            iconDrawable = painterResource(id = R.drawable.note_add_icon),
            isEnabled = true,
            iconDescription = stringResource(R.string.add_task)
        )
        if (homeUiState.isLoading)
            LoadingIndicator(
                modifier = Modifier
                    .zIndex(10f)
                    .align(Alignment.Center)
            )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopCuteTudeeAppBar(
                title = stringResource(R.string.app_title),
                description = stringResource(R.string.app_subtitle),
                isDark = homeUiState.isDarkMode,
                onSwitchTheme = onSwitchTheme,
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(AppTheme.color.surface),
                contentPadding = PaddingValues(bottom = 82.dp)
            ) {
                item {
                    OverlayBoxContent(
                        currentDate = homeUiState.currentDate,
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
                            tasks = homeUiState.inProgressTasks
                        )

                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        TaskSection(
                            title = stringResource(R.string.todo),
                            tasks = homeUiState.todoTasks
                        )

                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        TaskSection(
                            title = stringResource(R.string.done),
                            tasks = homeUiState.doneTasks
                        )
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 48.dp)
                                .padding(horizontal = 15.dp),
                            contentAlignment = Alignment.Center
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

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        HomeUiState(
            isLoading = false,
            currentDate = "2023-10-01",
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
        onSwitchTheme = {}
    )
}