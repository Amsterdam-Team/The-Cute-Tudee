package com.amsterdam.cutetudee.presentation.component.tast_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TaskStatusChip(
    taskStatusUi: TaskStatusUi,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(100),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
) {
    Row(
        modifier = modifier
            .background(
                color = taskStatusUi.containerColor,
                shape = shape
            )
            .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = taskStatusUi.contentColor,
                    shape = shape
                )
                .padding(2.5.dp)
        )
        Text(
            text = stringResource(taskStatusUi.labelRes),
            color = taskStatusUi.contentColor,
            style = AppTheme.textStyle.label.small
        )
    }
}
