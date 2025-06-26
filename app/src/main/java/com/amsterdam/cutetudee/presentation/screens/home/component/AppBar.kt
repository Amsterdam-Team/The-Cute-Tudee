package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.screens.home.component.theme_swithcer.ThemeSwitcherButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun AppBar(
    title: String,
    description: String,
    isDark: Boolean,
    onSwitchTheme: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .height(48.dp)
                    .background(
                        AppTheme.color.white40,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .border(
                        1.dp,
                        AppTheme.color.white40,
                        shape = RoundedCornerShape(12.dp),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.header_image),
                contentDescription = null,
                modifier = Modifier,
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                color = AppTheme.color.onPrimary,
                style = AppTheme.textStyle.appName.large,
            )
            Text(
                text = description,
                color = AppTheme.color.onPrimaryCaption,
                style = AppTheme.textStyle.label.small,
            )
        }
        ThemeSwitcherButton(
            isDark = isDark,
            onSwitchTheme = onSwitchTheme,
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun AppBarPreview(){
    AppBar(
        title = "",
        description = "",
        isDark = true,
        onSwitchTheme = {}
    )
}