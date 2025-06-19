package com.amsterdam.cutetudee.presentation.screens.category

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.BadgedCategoryItem
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    categoryViewModel: CategoryViewModel = koinViewModel()
) {

    val state by categoryViewModel.state.collectAsState()
    CategoryScreenContent(
        state = state,
        onNavigate = {
            //  navController.navigate()
        }, onClick = {}
    )
}

@Composable
private fun CategoryScreenContent(
    state: CategoryScreenUiState,
    onNavigate: () -> Unit,
    onClick: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.surface)
    ) {
        Box {
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
                    modifier = Modifier.padding(start=16.dp)
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
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
                        .clickable {
                            //  navController.navigate(screen = Screen.CategoryDetails(categoryUiState.categoryId))
                        },
                )
            }
        }
        CustomFloatingActionButton(
            onClick = { /*TODO*/ },
            isLoading = false,
            iconDrawable = painterResource(R.drawable.category_add_icon),
            isEnabled = true,
            iconDescription = null,
        )
    }
}

private fun getFakeCategories(context: Context): List<CategoryUiState> {
    return listOf(
        CategoryUiState(
            categoryId = "1",
            categoryImage = R.drawable.book_open_icon.toString(), // Use actual drawable IDs
            categoryName = "Books",
            badgeCount = "24"
        ),
        CategoryUiState(
            categoryId = "2",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Music",
            badgeCount = "15"
        ),
        CategoryUiState(
            categoryId = "3",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Movies",
            badgeCount = "8"
        ),
        CategoryUiState(
            categoryId = "4",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Games",
            badgeCount = "30"
        ),
        CategoryUiState(
            categoryId = "5",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Art",
            badgeCount = "12"
        ),
        CategoryUiState(
            categoryId = "6",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Food",
            badgeCount = "5"
        ),
        CategoryUiState(
            categoryId = "7",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Travel",
            badgeCount = "7"
        ),
        CategoryUiState(
            categoryId = "8",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Tech",
            badgeCount = "18"
        ),
        CategoryUiState(
            categoryId = "9",
            categoryImage = R.drawable.book_open_icon.toString(),
            categoryName = "Sports",
            badgeCount = "9"
        )
    )
}

@ThemeAndLocalePreviews
@Preview
@Composable
private fun PreviewCategoryScreen() {
    CuteTudeeTheme {
        val context = LocalContext.current
        CategoryScreenContent(
            state = CategoryScreenUiState(
                categories = getFakeCategories(context)
            ),
            onNavigate = { },
            onClick = { }
        )
    }
}