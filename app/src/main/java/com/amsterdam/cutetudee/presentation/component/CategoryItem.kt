package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.imageModel

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryImage: Uri,
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .clipToBounds()
                .background(AppTheme.color.surfaceHigh),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(AppTheme.color.surfaceHigh)
                    .padding(23.dp),
                model = imageModel(context, categoryImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = categoryName,
            style = AppTheme.textStyle.label.small,
            color = AppTheme.color.body,
            maxLines = 1
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun CategoryItemPreview() {
    CuteTudeeTheme {
        CategoryItem(
            categoryName = stringResource(R.string.education),
            categoryImage = Uri.EMPTY,
            modifier = Modifier.background(AppTheme.color.surface).width(100.dp)
        )
    }
}