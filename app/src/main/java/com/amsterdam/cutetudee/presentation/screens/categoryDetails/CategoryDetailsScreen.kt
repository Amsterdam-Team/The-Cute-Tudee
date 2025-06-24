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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
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
import com.amsterdam.cutetudee.presentation.utils.toBitmap
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
    categoryImage: String,
    modifier: Modifier = Modifier,
    onStatusChange: (Task.Status) -> Unit,
    onBack: () -> Unit,
    categoryTitle: String,
    onOptionClick: (Painter) -> Unit = {},
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
            onclickOption = {
                onOptionClick(
                    BitmapPainter(
                        categoryUiState.image.toBitmap().asImageBitmap()
                    )
                )
            },
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
                    categoryImage = BitmapPainter(categoryImage.toBitmap().asImageBitmap()),
                    priorityUi = enumValueOf<PriorityUi>(task.priority),
                    title = task.title,
                    description = task.description,
                    date = task.createdDate
                )
            }
        }

        AddEditCategoryBottomSheet(
            text = uiState.addBottomSheet.name,
            image = uiState.addBottomSheet.image,
            isLoading = uiState.addBottomSheet.isLoading,
            isEnabled = uiState.addBottomSheet.isEnabled,
            isEdit = true,
            painter = uiState.addBottomSheet.painter,
            hideBottomSheet = uiState.hideBottomSheet,
            onDeleteCategory = onDeleteCategory,
            onAddCategory = onEditCategory,
            onDismissRequest = onDismissRequest,
            onImageSelected = onImageSelected,
            onTextValueChange = onTextValueChange,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryDetailsScreen() {
    CuteTudeeTheme {
        CategoryDetailsContent(
            uiState = CategoryDetailsUiState(
                isLoading = false,
                errorMessage = "",
                addBottomSheet = /* You may need to provide a default BottomSheetState here if available */ com.amsterdam.cutetudee.presentation.screens.category.BottomSheetState(),
                hideBottomSheet = true,
                taskUiState = listOf(
                    TaskUiState(
                        id = "1",
                        title = "Buy groceries",
                        description = "Milk, Bread, Eggs",
                        priority = "HIGH",
                        status = "TODO",
                        createdDate = "2024-06-01",
                        categoryId = "cat1"
                    ),
                    TaskUiState(
                        id = "2",
                        title = "Read a book",
                        description = "Finish reading Kotlin in Action",
                        priority = "MEDIUM",
                        status = "IN_PROGRESS",
                        createdDate = "2024-06-02",
                        categoryId = "cat1"
                    ),
                    TaskUiState(
                        id = "3",
                        title = "Workout",
                        description = "30 min run",
                        priority = "LOW",
                        status = "DONE",
                        createdDate = "2024-06-03",
                        categoryId = "cat1"
                    )
                ),
                categoryUiState = CategoryUiState(
                    id = "cat1",
                    title = "Personal",
                    image = "",
                    isUserCreation = true
                )
            ),
            tasks = listOf(
                TaskUiState(
                    id = "1",
                    title = "Buy groceries",
                    description = "Milk, Bread, Eggs",
                    priority = "HIGH",
                    status = "TODO",
                    createdDate = "2024-06-01",
                    categoryId = "cat1"
                ),
                TaskUiState(
                    id = "2",
                    title = "Read a book",
                    description = "Finish reading Kotlin in Action",
                    priority = "MEDIUM",
                    status = "IN_PROGRESS",
                    createdDate = "2024-06-02",
                    categoryId = "cat1"
                ),
                TaskUiState(
                    id = "3",
                    title = "Workout",
                    description = "30 min run",
                    priority = "LOW",
                    status = "DONE",
                    createdDate = "2024-06-03",
                    categoryId = "cat1"
                )
            ),
            categoryUiState = CategoryUiState(
                id = "cat1",
                title = "Personal",
                image = "",
                isUserCreation = true
            ),
            selectedState = com.amsterdam.cutetudee.domain.model.Task.Status.TODO,
            categoryImage = "",
            onStatusChange = {},
            onBack = {},
            categoryTitle = "Personal",
            onOptionClick = {},
            onEditCategory = {},
            onDeleteCategory = {},
            onDismissRequest = {},
            onImageSelected = {},
            onTextValueChange = {},
        )
    }
}
