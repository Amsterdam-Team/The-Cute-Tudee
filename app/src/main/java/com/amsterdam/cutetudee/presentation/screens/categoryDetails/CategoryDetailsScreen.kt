package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.HorizontalTabs
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.Tab
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.TopAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel(),
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedState by viewModel.stateFilter.collectAsState()

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage.isNotEmpty() -> Text(text = "error ${uiState.errorMessage}")
        uiState.categoryUiState != null -> {
            CategoryDetailsContent(
                tasks = uiState.taskUiState,
                selectedState = selectedState,
                categoryUiState = uiState.categoryUiState!!,
                onStatusChange = viewModel::setStatus,
                onBack = { navController.popBackStack() },
                categoryTitle = uiState.categoryUiState!!.title,
                onOptionClick = { },
                categoryImage = uiState.categoryUiState!!.imageUrl.toInt()
            )
        }
    }
}

@Composable
private fun CategoryDetailsContent(
    tasks: List<TaskUiState>,
    categoryUiState: CategoryUiState,
    selectedState: Task.Status,
    categoryImage: Int,
    modifier: Modifier = Modifier,
    onStatusChange: (Task.Status) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding().background(color = AppTheme.color.surfaceHigh)
    ) {
        TopAppBar(
            onClickBack = onBack,
            title = categoryTitle,
            withOption = categoryUiState.isUserCreation,
            showIndicator = false,
            onclickOption = onOptionClick,
        )

        val inProgressCount = tasks.count { it.status == Task.Status.IN_PROGRESS.name }
        val toDoCount = tasks.count { it.status == Task.Status.TODO.name }
        val doneCount = tasks.count { it.status == Task.Status.DONE.name }

        HorizontalTabs(
            tabs = listOf(
                Tab(title = stringResource(R.string.in_progress), count = inProgressCount),
                Tab(title = stringResource(R.string.todo), count = toDoCount),
                Tab(title = stringResource(R.string.done), count = doneCount)
            ),
            selectedTabIndex = when (selectedState) {
                Task.Status.IN_PROGRESS -> 0
                Task.Status.TODO -> 1
                Task.Status.DONE -> 2
            },
            onTabSelected = {
                val selectedStatus = when (it) {
                    0 -> Task.Status.IN_PROGRESS
                    1 -> Task.Status.TODO
                    2 -> Task.Status.DONE
                    else -> Task.Status.IN_PROGRESS
                }
                onStatusChange(selectedStatus)
            }
        )
        val filteredTasks = tasks.filter { it.status == selectedState.name }
        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTasks) { task ->
                TaskItemCard(
                    categoryImage = painterResource(categoryImage),
                    priorityUi = enumValueOf<PriorityUi>(task.priority),
                    title = task.title,
                    description = task.description,
                    date = task.createdDate
                )
            }
        }
    }
}

@ThemeAndLocalePreviews
@Composable
fun CategoryDetailsPreview() {
    CuteTudeeTheme {
        val fakeTasks = listOf(
            TaskUiState(
                id = "kjsd",
                title = "Study Kotlin",
                description = "Read about coroutines and Flow",
                createdDate = "2025-06-16",
                status = Task.Status.IN_PROGRESS.name,
                priority = PriorityUi.HIGH.name,
                categoryId = "kjsd"
            ),
            TaskUiState(
                id = "kj5sd",
                title = "Write Report",
                description = "Project update",
                createdDate = "2025-06-14",
                status = Task.Status.DONE.name,
                priority = PriorityUi.MEDIUM.name,
                categoryId = "kjsd"
            ),
            TaskUiState(
                id = "k4jsd",
                title = "Write Report",
                description = "Project update",
                createdDate = "2025-06-14",
                status = Task.Status.DONE.name,
                priority = PriorityUi.MEDIUM.name,
                categoryId = "kjsd"
            ),
            TaskUiState(
                id = "3kjsd",
                title = "Study Kotlin",
                description = "Read about coroutines and Flow",
                createdDate = "2025-06-16",
                status = Task.Status.IN_PROGRESS.name,
                priority = PriorityUi.HIGH.name,
                categoryId = "kjsd"
            ),
            TaskUiState(
                id = "5kjsd",
                title = "Write Report",
                description = "Project update",
                createdDate = "2025-06-14",
                status = Task.Status.TODO.name,
                priority = PriorityUi.MEDIUM.name,
                categoryId = "kjsd"
            )
        )

        val selectedStatus = remember { mutableStateOf(Task.Status.TODO) }

        CategoryDetailsContent(
            modifier = Modifier.statusBarsPadding(),
            tasks = fakeTasks,
            categoryUiState = CategoryUiState(
                id = "ksdjk",
                title = "category 1",
                imageUrl = R.drawable.category_add_icon.toString(),
                isUserCreation = true
            ),
            selectedState = selectedStatus.value,
            onStatusChange = {
                selectedStatus.value = it
            },
            onBack = {},
            categoryTitle = "Coding",
            categoryImage = R.drawable.birthday_cake_icon,
        )
    }
}