package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
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
    Box(modifier = modifier) {
        CategoryItem(
            categoryName = categoryName,
            categoryImage = categoryImage,
            isAddedByUser = isAddedByUser,
            onClick = { onCategorySelected(categoryId) }
        )
        if (isSelected) {
            SelectedBadge(modifier = Modifier.align(Alignment.TopEnd))
        }
    }
}

@Composable
private fun SelectedBadge(
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(0.4f)
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.correct_badge_icon),
            contentDescription = "selected category icon",
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