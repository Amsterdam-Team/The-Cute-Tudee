package com.amsterdam.cutetudee.presentation.screens.category
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.BadgedCategoryItem
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun CategoryScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val state by categoryViewModel.state.collectAsState()
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(AppTheme.color.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.categories),
                style = AppTheme.textStyle.title.large,
                color = AppTheme.color.title
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(state.categories) { categoryUiState ->
                BadgedCategoryItem(
                    categoryItemUiState = CategoryItemUiState(
                        categoryImage = painterResource(categoryUiState.categoryImage.toInt()),
                        categoryName = categoryUiState.categoryName,
                        badgeCount = categoryUiState.badgeCount
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {},

                    )
            }
        }
        CustomFloatingActionButton(
            onClick = { /*TODO*/ },
            isLoading = false,
            iconDrawable = painterResource(R.drawable.category_add_icon),
            isEnabled = true,
            iconDescription = null,
            modifier = Modifier.padding(16.dp),
        )
    }
}
@ThemeAndLocalePreviews
@Preview
@Composable
private fun PreviewCategoryScreen() {
    CuteTudeeTheme {
        CategoryScreen(
            navController = NavController(LocalContext.current),
            onShowSnackBar = { _, _ -> }
        )
    }
}