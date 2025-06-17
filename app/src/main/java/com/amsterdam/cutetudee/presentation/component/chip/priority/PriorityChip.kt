package com.amsterdam.cutetudee.presentation.component.chip.priority

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.chip.BasicTaskChip
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun PriorityChip(
    priorityUi: PriorityUi,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val contentColor = if (selected) AppTheme.color.onPrimary else AppTheme.color.hint
    val containerColor =
        if (selected) priorityUi.selectedContainerColor else AppTheme.color.surfaceLow

    BasicTaskChip(
        modifier = modifier,
        labelRes = priorityUi.labelRes,
        containerColor = containerColor,
        contentColor = contentColor,
        spaceBetween = 2.dp,
        leadingIcon = {
            Icon(
                painter = painterResource(priorityUi.iconRes),
                contentDescription = stringResource(priorityUi.labelRes),
                tint = contentColor
            )
        }
    )
}

@Preview
@Composable
private fun PriorityChipPreview() {
    PriorityChip(
        priorityUi = PriorityUi.MEDIUM,
        selected = true
    )
}