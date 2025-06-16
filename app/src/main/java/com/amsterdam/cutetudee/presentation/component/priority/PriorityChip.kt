package com.amsterdam.cutetudee.presentation.component.priority

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun PriorityChip(
    priorityUi: PriorityUi,
    selected: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(100),
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
) {
    val contentColor = if (selected) AppTheme.color.onPrimary else AppTheme.color.hint
    val containerColor = if (selected) priorityUi.selectedContainerColor else AppTheme.color.surfaceLow
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
            painter = painterResource(priorityUi.iconRes),
            contentDescription = stringResource(priorityUi.labelRes),
            tint = contentColor
        )
        Text(
            text = stringResource(priorityUi.labelRes),
            color = contentColor,
            style = AppTheme.textStyle.label.small
        )
    }
}