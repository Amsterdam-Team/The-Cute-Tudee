package com.amsterdam.cutetudee.presentation.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.screens.category.composables.AddEditCategoryBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {

    val uiState = viewModel.state.collectAsStateWithLifecycle()


    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
        LazyRow {
            items(uiState.value.categories) {
                Image(
                    painter = it.categoryImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            viewModel.onHideBottomSheet(it.categoryImage)
                            viewModel.onEditCategoryName(it.categoryName)
                        }
                )
            }
        }


        AddEditCategoryBottomSheet(
            text = uiState.value.addBottomSheet.name,
            image = uiState.value.addBottomSheet.image,
            isLoading = uiState.value.addBottomSheet.isLoading,
            isEnabled = uiState.value.addBottomSheet.isEnabled,
            isEdit = true,
            painter = uiState.value.addBottomSheet.painter,
            hideBottomSheet = uiState.value.hideBottomSheet,
            onAddCategory = viewModel::addCategory,
            onDismissRequest = viewModel::dismissBottomSheet,
            onImageSelected = viewModel::updateCategoryImage,
            onTextValueChange = viewModel::updateCategoryName
        )
    }
}