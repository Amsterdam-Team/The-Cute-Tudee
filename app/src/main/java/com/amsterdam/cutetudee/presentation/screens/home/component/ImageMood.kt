package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun ImageMood(
    image: Painter
) {
    Box(
        modifier = Modifier
            .offset(y = 8.dp, x = 2.dp)
            .size(65.dp)
            .clip(CircleShape)
            .alpha(0.16f)
            .background(AppTheme.color.primary),
    )
    Image(
        painter = image,
        contentDescription = null,
    )
}

@Composable
@ThemeAndLocalePreviews
private fun ImageMoodPreview() {
    CuteTudeeTheme {
        ImageMood(
            image = painterResource(id = R.drawable.tudee_image_neutral),
        )
    }
}
