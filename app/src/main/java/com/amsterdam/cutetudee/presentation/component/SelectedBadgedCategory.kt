package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun SelectedBadgedCategory(
    categoryId: String,
    categoryName: String,
    categoryImage: Painter,
    isSelected: Boolean,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = {
                    onCategorySelected(
                        categoryId
                    )
                }
            )
    ) {
        CategoryItem(
            categoryName = categoryName,
            categoryImage = categoryImage
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
        modifier = modifier
            .width(36.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(AppTheme.color.surfaceLow)
            .padding(2.dp)
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.correct_badge_icon),
            contentDescription = "selected category icon",
            tint = AppTheme.color.purpleAccent
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
            categoryImage = painterResource(R.drawable.book_open_icon),
            isSelected = true,
            onCategorySelected = {},
            categoryId = "1"
        )
    }
}