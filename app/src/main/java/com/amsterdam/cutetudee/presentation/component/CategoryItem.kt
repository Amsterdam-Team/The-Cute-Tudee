package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme


@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryImage: Painter,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.background(AppTheme.color.surface)
    ) {
        Icon(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(CircleShape)
                .background(AppTheme.color.surfaceHigh)
                .padding(23.dp),
            painter = categoryImage,
            contentDescription = null,
            tint = AppTheme.color.purpleAccent
        )

        Text(
            text = categoryName,
            style = AppTheme.textStyle.label.small,
            color = AppTheme.color.body
        )
    }
}


@Preview(showBackground = true, widthDp = 300, heightDp = 1000)
@Composable
private fun CategoryItemPreview() {
    CategoryItem(
        categoryImage = painterResource(R.drawable.book_open_icon),
        categoryName = "Education",

        )
}