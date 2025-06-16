package com.amsterdam.cutetudee.presentation.component.priority

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun PriorityChip(
    @StringRes label: Int,
    icon: Painter,
    selected: Boolean,
    selectedContainerColor: Color,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
) {
    val contentColor = if (selected) AppTheme.color.onPrimary else AppTheme.color.hint
    val containerColor = if (selected) selectedContainerColor else AppTheme.color.surfaceLow
    Row(
        modifier = modifier
            .background(
                color = containerColor,
                shape = shape
            )
            .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(label),
            tint = contentColor
        )
        Text(
            text = stringResource(label),
            color = contentColor,
            style = AppTheme.textStyle.label.small
        )
    }
}