package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryImage: Painter,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(AppTheme.color.surfaceHigh)
                .padding(23.dp),
            painter = categoryImage,
            contentDescription = null,
        )
        Text(
            text = categoryName,
            style = AppTheme.textStyle.label.small,
            color = AppTheme.color.body
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun CategoryItemPreview() {
    CuteTudeeTheme {
        CategoryItem(
            categoryName = stringResource(R.string.education),
            categoryImage = painterResource(R.drawable.book_open_icon),
            modifier = Modifier.background(AppTheme.color.surface).width(100.dp)
        )
    }
}