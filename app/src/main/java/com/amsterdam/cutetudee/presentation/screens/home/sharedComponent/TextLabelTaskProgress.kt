package com.amsterdam.cutetudee.presentation.screens.home.sharedComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun TextLabelTaskProgress(
    label: String,
    numbersOfItems: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = AppTheme.textStyle.title.large,
            color = AppTheme.color.title,
        )
        TextWithArrowIcon(
            numbersOfItems = numbersOfItems,
            onClick = onClick,
        )
    }
}

@Composable
@ThemeAndLocalePreviews
private fun TextLabelTaskProgressPreview() {
    TextLabelTaskProgress(
        label = "",
        numbersOfItems = 9,
        onClick = {},

        )
}
