package com.amsterdam.cutetudee.presentation.component.priority

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun HighPriorityCard(
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    BasicPriorityCard(
        modifier = modifier,
        label = R.string.priority_high,
        icon = painterResource(R.drawable.flag_icon),
        selected = selected,
        selectedContainerColor = AppTheme.color.pinkAccent
    )
}

@Preview(showBackground = true)
@Composable
private fun HighPriorityCardPreview() {
    HighPriorityCard(
        selected = true
    )
}