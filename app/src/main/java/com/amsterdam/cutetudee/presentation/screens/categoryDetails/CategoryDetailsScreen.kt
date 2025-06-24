package com.amsterdam.cutetudee.presentation.screens.categoryDetails

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.ConfirmationBottomSheet
import com.amsterdam.cutetudee.presentation.component.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.composables.AddEditCategoryBottomSheet
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.HorizontalTabs
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.Tab
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.component.TopAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryDetailsScreen(
    viewModel: CategoryDetailsViewModel = koinViewModel(),
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
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
            .navigationBarsPadding()
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

        HorizontalTabs(
            tabs = listOf(
                Tab(
                    title = stringResource(R.string.in_progress),
                    count = uiState.categoryItemUiState.inProgressTasksCount
                ),
                Tab(
                    title = stringResource(R.string.todo),
                    count = uiState.categoryItemUiState.toDoTasksCount
                ),
                Tab(
                    title = stringResource(R.string.done),
                    count = uiState.categoryItemUiState.doneTasksCount
                )
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
                detailsInteractionListener.onTaskStatusChanged(selectedStatus)
            }
        )
        val filteredTasks = uiState.taskUiState.filter { it.status == selectedState.name }
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
                    date = task.createdDate
                )
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