package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews


@Composable
fun BadgedCategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryImage: Uri,
    badgeCount: String,
    isAddedByUser: Boolean = false,
) {
    Box(modifier = modifier) {
        CategoryItem(
            categoryName = categoryName,
            categoryImage = categoryImage,
            isAddedByUser = isAddedByUser,
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
                text = badgeCount,
                style = AppTheme.textStyle.label.small,
                color = AppTheme.color.hint
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun BadgedCategoryItemPreview() {
    CuteTudeeTheme {
        BadgedCategoryItem(
            categoryName = stringResource(R.string.education),
            categoryImage = Uri.EMPTY,
            badgeCount = "16",
            modifier = Modifier.background(AppTheme.color.surface).width(100.dp)
        )
    }
}