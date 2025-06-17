package com.amsterdam.cutetudee.presentation.component.chip

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun BasicChip(
    @StringRes labelRes: Int,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(100.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
    leadingIcon: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = containerColor,
                shape = shape
            )
            .padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(spaceBetween, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        Text(
            text = stringResource(labelRes),
            color = contentColor,
            style = AppTheme.textStyle.label.small
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicChipPreview() {
    BasicChip(
        labelRes = TaskStatusUi.TODO.labelRes,
        containerColor = TaskStatusUi.TODO.containerColor,
        contentColor = TaskStatusUi.TODO.contentColor,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        spaceBetween = 4.dp,
        leadingIcon = { }
    )
}