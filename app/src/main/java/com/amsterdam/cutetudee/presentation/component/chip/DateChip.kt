package com.amsterdam.cutetudee.presentation.component.chip

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun DateChip(
    date: String,
    modifier: Modifier = Modifier,
    contentColor: Color = AppTheme.color.body
) {
    BasicChip(
        modifier = modifier,
        label = date,
        containerColor = AppTheme.color.surface,
        contentColor = contentColor,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.date_icon),
                contentDescription = "calendar icon",
                tint = contentColor
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DateChipPreview() {
    DateChip(
        date = "12-03-2025"
    )
}