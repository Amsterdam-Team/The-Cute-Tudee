package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TopCuteTudeeAppBar(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isDark: Boolean,
    onSwitchTheme: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.color.primary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .height(72.dp)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppBar(
                title = title,
                description = description,
                isDark = isDark,
                onSwitchTheme = onSwitchTheme
            )
        }
    }

}


@Composable
@ThemeAndLocalePreviews
private fun TopCuteTudeeAppBarPreview() {
    TopCuteTudeeAppBar(
        title = "Tudee",
        description = "Your cute Helper for Every Task",
        isDark = false,
        onSwitchTheme = {})
}

