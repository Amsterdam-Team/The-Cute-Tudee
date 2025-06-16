package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.textStyle.defaultTextStyle

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(100.dp))
            .background(AppTheme.color.errorVariant)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.delete),
            style = AppTheme.textStyle.label.large,
            color = AppTheme.color.error
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun NavigationButtonPreview(){
    NavigationButton()
}