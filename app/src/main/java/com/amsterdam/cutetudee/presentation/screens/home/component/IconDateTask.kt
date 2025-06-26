package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
fun IconDateTask(
    date: String, icon: Painter, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp),
            tint = AppTheme.color.body
        )
        Text(
            text = stringResource(R.string.today, date),
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun IconDateTaskPreview() {
    IconDateTask(
        date = "today, 22 Jun 2025", icon = painterResource(id = R.drawable.date_icon)
    )
}