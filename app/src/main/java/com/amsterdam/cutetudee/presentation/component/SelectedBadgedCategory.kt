package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isAddedByUser: Boolean = false,
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
            isAddedByUser = isAddedByUser
        )
        AnimatedVisibility(isSelected,modifier = Modifier.align(Alignment.TopEnd)) {
            SelectedBadge()
        }
    }
}

@Composable
private fun SelectedBadge(
    modifier: Modifier = Modifier
) {
        Image(
            modifier = modifier.size(20.dp).clip(CircleShape),
            painter = painterResource(id = R.drawable.correct_badge_icon),
            contentDescription = "selected category icon",
        )
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