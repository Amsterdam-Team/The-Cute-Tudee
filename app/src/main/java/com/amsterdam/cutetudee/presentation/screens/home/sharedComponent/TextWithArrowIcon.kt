package com.amsterdam.cutetudee.presentation.screens.home.sharedComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.mirroredContent

@Composable
fun TextWithArrowIcon(
    numbersOfItems: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(AppTheme.color.surfaceHigh)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = "$numbersOfItems",
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body,
        )
        Icon(
            painter = painterResource(R.drawable.arrow),
            contentDescription = null,
            tint = AppTheme.color.body,
            modifier = Modifier.mirroredContent(LocalLayoutDirection.current),
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TextWithArrowIconPreview() {
    TextWithArrowIcon(
        numbersOfItems = 12,
        onClick = {},
    )
}
