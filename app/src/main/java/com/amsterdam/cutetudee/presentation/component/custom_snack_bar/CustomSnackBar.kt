package com.amsterdam.cutetudee.presentation.component.custom_snack_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun BoxScope.CustomSnackBar(message: String, status: CustomSnackBarStatus) {
    Box(Modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
                .padding(top = 3.dp)
                .fillMaxWidth()
                .dropShadow(
                    blur = 4.dp,
                    offsetY = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = AppTheme.color.dropShadowColor
                )
                .clip(shape = RoundedCornerShape(12.dp))
                .clipToBounds()
                .background(AppTheme.color.surfaceHigh, shape = RoundedCornerShape(12.dp))

        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 56.dp)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(status.icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(status.iconTintColor),
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = status.iconContainerColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                )
                Text(
                    text = message,
                    style = AppTheme.textStyle.body.medium,
                    color = AppTheme.color.body,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@PreviewLightDark()
@Composable
private fun CustomSnackBarSuccessPreview() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        Box {
            CustomSnackBar(
                "This is a success snack bar",
                CustomSnackBarStatus.Success
            )
        }
    }
}

@PreviewLightDark()
@Composable
private fun CustomSnackBarFailurePreview() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        Box {
            CustomSnackBar(
                "This is a failure snack bar",
                CustomSnackBarStatus.Failure
            )
        }
    }
}
