package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun ImageMood(
    image: Painter
) {
    Box(
        modifier = Modifier
            .offset(y = 8.dp, x = 2.dp)
            .size(65.dp)
            .background(
                color = AppTheme.color.overlay, shape = RoundedCornerShape(100)
            )
    )
    Image(
        painter = image,
        contentDescription = null,
    )
}

@Composable
@ThemeAndLocalePreviews
private fun ImageMoodPreview() {
    ImageMood(
        image = painterResource(id = com.amsterdam.cutetudee.R.drawable.tudee_image_neutral),
    )
}