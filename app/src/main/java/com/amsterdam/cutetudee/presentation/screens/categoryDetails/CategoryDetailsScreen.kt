package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.LocalNavController
import com.amsterdam.cutetudee.presentation.component.TabsContent
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.CategoryEffect
import com.amsterdam.cutetudee.presentation.screens.category.composables.AddEditCategoryBottomSheet
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.TopAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel(),
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    val navController = LocalNavController.current
    val uiState by viewModel.state.collectAsState()
    val selectedState by viewModel.stateFilter.collectAsState()
    val addSuccessMessage = stringResource(R.string.add_category_success)
    val editSuccessMessage = stringResource(R.string.edit_category_success)
    val failMessage = stringResource(R.string.error_unknown)
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CategoryEffect.ShowAddSnackBar -> {
                    onShowSnackBar(
                        addSuccessMessage,
                        CustomSnackBarStatus.Success
                    )
                }

                CategoryEffect.ShowEditSnackBar -> {
                    onShowSnackBar(
                        editSuccessMessage,
                        CustomSnackBarStatus.Success
                    )
                    navController.popBackStack()
                }

                CategoryEffect.ShowError -> {
                    onShowSnackBar(
                        failMessage,
                        CustomSnackBarStatus.Failure
                    )
                }

                CategoryEffect.DeleteEffect -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage.isNotEmpty() -> Text(text = "error ${uiState.errorMessage}")
        else -> {
            CategoryDetailsContent(
                uiState = uiState,
                tasks = uiState.taskUiState,
                selectedState = selectedState,
                categoryUiState = uiState.categoryUiState,
                onStatusChange = viewModel::setStatus,
                onBack = { navController.popBackStack() },
                categoryTitle = uiState.categoryUiState.title,
                onOptionClick = viewModel::onToggleBottomSheet,
                categoryImage = uiState.categoryUiState.image,
                onEditCategory = viewModel::editCategory,
                onDeleteCategory = viewModel::deleteCategory,
                onDismissRequest = viewModel::dismissBottomSheet,
                onImageSelected = viewModel::updateCategoryImage,
                onTextValueChange = viewModel::updateCategoryName
            )
        }
    }
}

@Composable
private fun CategoryDetailsContent(
    uiState: CategoryDetailsUiState,
    tasks: List<TaskUiState>,
    categoryUiState: CategoryUiState,
    selectedState: Task.Status,
    categoryImage: Uri,
    modifier: Modifier = Modifier,
    onStatusChange: (Task.Status) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: (Uri) -> Unit = {},
    onEditCategory: () -> Unit,
    onDeleteCategory: () -> Unit,
    onDismissRequest: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onTextValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppTheme.color.surfaceHigh)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TopAppBar(
            onClickBack = onBack,
            title = categoryTitle,
            withOption = categoryUiState.isUserCreation,
            showIndicator = false,
            onclickOption = { onOptionClick(categoryUiState.image) },
        )

        // Map selectedState (Task.Status) to TaskStatusUi
        val selectedStatusUi = when (selectedState) {
            Task.Status.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
            Task.Status.TODO -> TaskStatusUi.TODO
            Task.Status.DONE -> TaskStatusUi.DONE
        }
        // Count tasks by status
        val filteredTasks = tasks.filter { it.status == selectedState.name }
        val numberOfTasks = filteredTasks.size

        TabsContent(
            selectedStatus = selectedStatusUi,
            numberOfTasks = numberOfTasks,
            onTabChange = { statusUi ->
                val status = when (statusUi) {
                    TaskStatusUi.IN_PROGRESS -> Task.Status.IN_PROGRESS
                    TaskStatusUi.TODO -> Task.Status.TODO
                    TaskStatusUi.DONE -> Task.Status.DONE
                }
                onStatusChange(status)
            },
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.surface)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredTasks) { task ->
                TaskItemCard(
                    categoryImage = categoryImage,
                    priorityUi = enumValueOf<PriorityUi>(task.priority),
                    title = task.title,
                    description = task.description,
                )
            }
        }

        AddEditCategoryBottomSheet(
            text = uiState.addBottomSheet.name,
            image = uiState.addBottomSheet.image,
            isLoading = uiState.addBottomSheet.isLoading,
            isEnabled = uiState.addBottomSheet.isEnabled,
            isEdit = true,
            hideBottomSheet = uiState.hideBottomSheet,
            onDeleteCategory = onDeleteCategory,
            onAddCategory = onEditCategory,
            onDismissRequest = onDismissRequest,
            onImageSelected = onImageSelected,
            onTextValueChange = onTextValueChange,
        )
    }
}

