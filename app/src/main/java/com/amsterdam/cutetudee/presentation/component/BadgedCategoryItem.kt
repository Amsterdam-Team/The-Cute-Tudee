package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.screens.category.CategoryItemUiState
import com.amsterdam.cutetudee.presentation.theme.AppTheme


@Composable
fun BadgedCategoryItem(
    modifier: Modifier = Modifier,
    categoryItemUiState: CategoryItemUiState
) {
    Box(modifier = modifier) {
        CategoryItem(
            categoryItemUiState = categoryItemUiState
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(36.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(AppTheme.color.surfaceLow)
                .padding(2.dp)
                .align(Alignment.TopEnd)
        ) {
            Text(
                text = categoryItemUiState.badgeCount,
                style = AppTheme.textStyle.label.small,
                color = AppTheme.color.hint
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BadgedCategoryItemPreview() {
    BadgedCategoryItem(
        categoryItemUiState = CategoryItemUiState(
            painterResource(R.drawable.book_open_icon),
            "Education",
            "16"
        ),
    )
}

