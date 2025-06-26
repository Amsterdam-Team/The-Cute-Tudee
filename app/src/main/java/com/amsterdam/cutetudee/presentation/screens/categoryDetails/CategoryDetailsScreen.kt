package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.LocalNavController
import com.amsterdam.cutetudee.presentation.component.ConfirmationBottomSheet
import com.amsterdam.cutetudee.presentation.component.NoTasksContainer
import com.amsterdam.cutetudee.presentation.component.TabsContent
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.component.AddEditCategoryBottomSheet
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.TopAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
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
    val editSuccessMessage = stringResource(R.string.edit_category_success)
    val deleteSuccessMessage = stringResource(R.string.delete_category_success)
    val failMessage = stringResource(R.string.error_unknown)
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CategoryDetailsEffect.ShowEditSnackBar -> {
                    onShowSnackBar(
                        editSuccessMessage,
                        CustomSnackBarStatus.Success
                    )
                    navController.popBackStack()
                }

                CategoryDetailsEffect.ShowDeleteSnackBar -> {
                    onShowSnackBar(
                        deleteSuccessMessage,
                        CustomSnackBarStatus.Success
                    )
                }

                CategoryDetailsEffect.ShowError -> {
                    onShowSnackBar(
                        failMessage,
                        CustomSnackBarStatus.Failure
                    )
                }

                CategoryDetailsEffect.NavigateBack -> {
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

        else -> {
            CategoryDetailsContent(
                uiState = uiState,
                selectedState = selectedState,
                detailsInteractionListener = viewModel,
                editInteractionListener = viewModel,
                deleteInteractionListener = viewModel
            )
        }
    }
}

@Composable
private fun CategoryDetailsContent(
    uiState: CategoryDetailsUiState,
    selectedState: Task.Status,
    detailsInteractionListener: CategoryDetailsInteractionListener,
    editInteractionListener: CategoryEditInteractionListener,
    deleteInteractionListener: CategoryDeleteConfirmationInteractionListener,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppTheme.color.surfaceHigh)
            .statusBarsPadding()
    ) {
        TopAppBar(
            onClickBack = detailsInteractionListener::onNavigateBackClicked,
            title = uiState.categoryItemUiState.title,
            withOption = uiState.categoryItemUiState.isUserCreation,
            showIndicator = false,
            onclickOption = {
                detailsInteractionListener.onEditOptionClicked(
                    name = uiState.categoryItemUiState.title,
                    uri = uiState.categoryItemUiState.image,
                )
            },
        )

        val selectedStatusUi = when (selectedState) {
            Task.Status.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
            Task.Status.TODO -> TaskStatusUi.TODO
            Task.Status.DONE -> TaskStatusUi.DONE
        }
        val filteredTasks = uiState.taskUiState.filter { it.status == selectedState.name }
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
                detailsInteractionListener.onTaskStatusChanged(status)
            },
        )
        if (filteredTasks.isEmpty())
            Box(
                modifier =
                    Modifier
                        .fillMaxSize().padding(horizontal = 12.dp)
                        .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                NoTasksContainer(
                    primaryMessage = stringResource(R.string.empty_tasks_title),
                    secondaryMessage = stringResource(R.string.no_tasks_for_category, uiState.categoryItemUiState.title)
                )
            }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.color.surface)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredTasks) { task ->
                    TaskItemCard(
                        categoryImage = uiState.categoryItemUiState.image,
                        priorityUi = enumValueOf<PriorityUi>(task.priority),
                        title = task.title,
                        description = task.description,
                    )
                }
                item {
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
        }

        AddEditCategoryBottomSheet(
            text = uiState.categoryBottomSheetState.name,
            image = uiState.categoryBottomSheetState.image,
            isLoading = uiState.categoryBottomSheetState.isLoading,
            isEnabled = uiState.categoryBottomSheetState.isEnabled,
            isEdit = true,
            hideBottomSheet = uiState.hideEditBottomSheet,
            onDeleteCategory = editInteractionListener::onDeleteCategoryClicked,
            onAddCategory = editInteractionListener::onSaveCategoryClicked,
            onDismissRequest = editInteractionListener::onDismissEditSheet,
            onCancel = editInteractionListener::onCancelEditCategoryClicked,
            onImageSelected = editInteractionListener::onUpdateCategoryImage,
            onTextValueChange = editInteractionListener::onUpdateCategoryTextValue,
        )
        ConfirmationBottomSheet(
            isVisible = uiState.showDeleteConfirmBottomSheet,
            onAction = deleteInteractionListener::onDeleteConfirmationClicked,
            onCancel = deleteInteractionListener::onCancelDeleteConfirmationClicked,
            onDismiss = deleteInteractionListener::onDismissDeleteConfirmationSheet
        )
    }
}

