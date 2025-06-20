package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.screens.home.component.theme_swithcer.ThemeSwitcherButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TopCuteTudeeAppBar(
    title: String, description: String, modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(AppTheme.color.primary)
            .padding(horizontal = 16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .height(72.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isDark = remember { mutableStateOf(false) }
            TitleAndLogoAppBar(
                title = title,
                description = description,
                isDark = isDark.value,
                onCheckedChange = {
                    isDark.value = it
                }
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
    )
}

