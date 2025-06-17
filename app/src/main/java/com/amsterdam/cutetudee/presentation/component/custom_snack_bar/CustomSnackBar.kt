package com.amsterdam.cutetudee.presentation.component.custom_snack_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun BoxScope.CustomSnackBar(state: CustomSnackBarState) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .clipToBounds()
            .background(AppTheme.color.surfaceHigh)
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .defaultMinSize(minHeight = 56.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(state.status.icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(state.status.iconTintColor),
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = state.status.iconContainerColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
            )
            Text(
                text = state.message,
                style = AppTheme.textStyle.body.medium,
                color = AppTheme.color.body,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CustomSnackBarSuccessPreview() {
    Box {
        CustomSnackBar(state = CustomSnackBarState("This is a success snack bar", CustomSnackBarStatus.Success))
    }
}

@Preview
@Composable
private fun CustomSnackBarFailurePreview() {
    Box {
        CustomSnackBar(state = CustomSnackBarState("This is a failure snack bar", CustomSnackBarStatus.Failure))
    }
}