package com.amsterdam.cutetudee.presentation.component.priority

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun MediumPriorityCard(
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    BasicPriorityCard(
        modifier = modifier,
        label = R.string.priority_medium,
        icon = painterResource(R.drawable.alert_icon),
        selected = selected,
        selectedContainerColor = AppTheme.color.yellowAccent
    )
}

@Preview(showBackground = true)
@Composable
private fun MediumPriorityCardPreview() {
    MediumPriorityCard(
        selected = true
    )
}