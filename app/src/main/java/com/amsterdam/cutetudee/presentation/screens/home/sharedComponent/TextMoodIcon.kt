package com.amsterdam.cutetudee.presentation.screens.home.sharedComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TextMoodIcon(
    text: String, icon: Painter, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text, style = AppTheme.textStyle.title.small, color = AppTheme.color.title
        )
        Image(
            painter = icon, contentDescription = null
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun TextMoodIconPreview() {
    TextMoodIcon(
        text = stringResource(R.string.update_neutral_title),
        icon = painterResource(id = R.drawable.bad_emoji_icon),
    )
}
