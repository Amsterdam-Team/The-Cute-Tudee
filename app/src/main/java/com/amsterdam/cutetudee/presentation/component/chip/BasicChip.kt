package com.amsterdam.cutetudee.presentation.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.toTaskPriority
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun BasicChip(
    label: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(100.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
    leadingIcon: @Composable () -> Unit = {},
    onClick: (Task.Priority) -> Unit
) {
    val isClicked = remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .background(
                color = containerColor,
                shape = shape
            )
            .clickable(
                onClick = { isClicked.value = !isClicked.value })
            .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(spaceBetween, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        Text(
            text = label,
            color = contentColor,
            style = AppTheme.textStyle.label.small
        )
    }

    if (isClicked.value) {
        onClick(label.toTaskPriority())
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicChipPreview() {
    BasicChip(
        label = stringResource(TaskStatusUi.TODO.labelRes),
        containerColor = TaskStatusUi.TODO.containerColor,
        contentColor = TaskStatusUi.TODO.contentColor,
        spaceBetween = 4.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        leadingIcon = { },
        onClick = { }
    )
}