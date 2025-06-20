package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun ConfirmationMessageContainer(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.delete_task),
    description: String = stringResource(R.string.delete_description),
    image: Painter = painterResource(R.drawable.tudee_image_shocking)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.surface)
            .padding(horizontal = 16.dp)
    )
    {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = title,
            style = AppTheme.textStyle.title.large,
            color = AppTheme.color.title
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = description,
            style = AppTheme.textStyle.body.large,
            color = AppTheme.color.body
        )
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .width(107.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@ThemeAndLocalePreviews
@Composable
fun ConfirmationMessageContainerPreview() {
    ConfirmationMessageContainer()
}