package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.BadgedCategoryItem
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.NoRippleInteractionSource
import com.amsterdam.cutetudee.presentation.utils.bottomNavigationBarPadding
import org.koin.androidx.compose.koinViewModel
import com.amsterdam.cutetudee.presentation.component.CategoryInteractionListener.AddEditCategoryBottomSheet
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val addSuccessMessage = stringResource(R.string.add_category_success)
    val editSuccessMessage = stringResource(R.string.edit_category_success)
        val failMessage = stringResource(R.string.error_unknown)
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect){
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
                }
                CategoryEffect.ShowError -> {
                    onShowSnackBar(
                        failMessage,
                        CustomSnackBarStatus.Failure
                    )
                }

                CategoryEffect.DeleteEffect -> {}
            }
        }
    }
    CategoryScreenContent(
        state = state,
        onNavigate = {
            navController.navigate(it)
        },
        onFabClick = viewModel::onToggleBottomSheet,
        onAddCategory = viewModel::addCategory,
        onDismissRequest = viewModel::dismissBottomSheet,
        onImageSelected = viewModel::updateCategoryImage,
        onTextValueChange = viewModel::updateCategoryName
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoryScreenContent(
    state: CategoryScreenUiState,
    onNavigate: (Any) -> Unit,
    onFabClick: () -> Unit,
    onAddCategory: () -> Unit,
    onDismissRequest: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onTextValueChange: (String) -> Unit,
) {
    Box(Modifier.fillMaxSize().bottomNavigationBarPadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.surfaceHigh)
                .navigationBarsPadding()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(AppTheme.color.surfaceHigh),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.categories),
                    style = AppTheme.textStyle.title.large,
                    color = AppTheme.color.title,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(34.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .background(AppTheme.color.surface)
                    .padding(horizontal = 16.dp)
            ) {
                items(state.categories) { categoryUiState ->
                    BadgedCategoryItem(
                        categoryImage = categoryUiState.categoryImage,
                        categoryName = categoryUiState.categoryName,
                        badgeCount = categoryUiState.badgeCount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .combinedClickable(NoRippleInteractionSource, null) {
                                onNavigate(Screen.CategoryDetails(categoryUiState.categoryId))
                            },
                    )
                }
            }
        }
        AddEditCategoryBottomSheet(
            text = state.addBottomSheet.name,
            image = state.addBottomSheet.image,
            isLoading = state.addBottomSheet.isLoading,
            isEnabled = state.addBottomSheet.isEnabled,
            isEdit = true,
            painter = state.addBottomSheet.painter,
            hideBottomSheet = state.hideBottomSheet,
            onAddCategory = onAddCategory,
            onDismissRequest = onDismissRequest,
            onImageSelected = onImageSelected,
            onTextValueChange = onTextValueChange,
        )
        CustomFloatingActionButton(
            onClick = onFabClick,
            isLoading = false,
            iconDrawable = painterResource(R.drawable.category_add_icon),
            isEnabled = true,
            iconDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 12.dp)
        )
    }
}
