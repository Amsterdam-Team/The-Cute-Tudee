package com.amsterdam.cutetudee.presentation.component.chip.tast_status

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.BasicChip
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TaskStatusChip(
    taskStatusUi: TaskStatusUi,
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
    onclick: ((Task.Status) -> Unit)? = null
) {
    val contentColor by animateColorAsState(if (isSelected) taskStatusUi.contentColor else AppTheme.color.hint)
    val containerColor by animateColorAsState(if (isSelected) taskStatusUi.containerColor else AppTheme.color.surfaceLow)

    BasicChip(
        label = stringResource(taskStatusUi.labelRes),
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = modifier,
        spaceBetween = 4.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        leadingIcon = {
            Box(
                modifier = Modifier
                    .background(
                        color = taskStatusUi.contentColor,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(2.5.dp)
            )
        },
        onClick = if (onclick != null) { { onclick(taskStatusUi.toTaskStatus()) } } else null
    )
}

@Preview
@Composable
private fun TaskStatusChipPreview() {
    TaskStatusChip(
        taskStatusUi = TaskStatusUi.DONE,
        isSelected = false,
    )
}
