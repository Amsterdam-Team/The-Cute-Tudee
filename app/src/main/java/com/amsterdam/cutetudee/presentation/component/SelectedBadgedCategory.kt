package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun SelectedBadgedCategory(
    categoryId: String,
    categoryName: String,
    categoryImage: Uri,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    isAddedByUser: Boolean = false,
    onCategorySelected: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = {
                    onCategorySelected(
                        categoryId
                    )
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        CategoryItem(
            categoryName = categoryName,
            categoryImage = categoryImage,
            isAddedByUser = isAddedByUser,
            isSelected = isSelected
        )

    }
}



@ThemeAndLocalePreviews
@Composable
fun SelectedBadgedCategoryPreview() {
    CuteTudeeTheme {
        SelectedBadgedCategory(
            modifier = Modifier,
            categoryName = "Category",
            categoryImage = Uri.EMPTY,
            isSelected = true,
            onCategorySelected = {},
            categoryId = "1"
        )
    }
}