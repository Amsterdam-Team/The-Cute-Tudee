package com.amsterdam.cutetudee.presentation.component.chip.tast_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.chip.BasicTaskChip

@Composable
fun TaskStatusChip(
    taskStatusUi: TaskStatusUi,
    modifier: Modifier = Modifier
) {
    BasicTaskChip(
        modifier = modifier,
        labelRes = taskStatusUi.labelRes,
        containerColor = taskStatusUi.containerColor,
        contentColor = taskStatusUi.contentColor,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        spaceBetween = 4.dp,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .background(
                        color = taskStatusUi.contentColor,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(2.5.dp)
            )
        }
    )
}

@Preview
@Composable
private fun TaskStatusChipPreview() {
    TaskStatusChip(
        taskStatusUi = TaskStatusUi.DONE,
    )
}
