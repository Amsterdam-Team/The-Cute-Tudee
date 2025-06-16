package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TudeeFloatingActionButton(
    onClick: () -> Unit,
    isEnabled: Boolean,
    isLoading: Boolean,
    iconDrawable: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
) {
    val containerColor =
        if (!isEnabled) {
            Modifier.background(AppTheme.color.disable)
        } else {
            Modifier.background(
                Brush.verticalGradient(
                    listOf(
                        AppTheme.color.primaryGradientStart,
                        AppTheme.color.primaryGradientEnd,
                    ),
                ),
            )
        }

    val contentColor =
        if (!isEnabled) {
            AppTheme.color.stroke
        } else {
            AppTheme.color.onPrimary
        }

    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.Transparent,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        shape = CircleShape,
        modifier =
            modifier
                .size(64.dp)
                .clip(CircleShape)
                .then(containerColor),
    ) {
        if (isLoading && isEnabled) {
            CustomAnimatedProgressIndicatior(
                tint = contentColor,
            )
        } else {
            Icon(
                imageVector = iconDrawable,
                contentDescription = iconDescription,
            )
        }
    }
}

@Preview(name = "TudeeFloatingActionButton")
@Composable
private fun PreviewTudeeFloatingActionButton() {
    TudeeFloatingActionButton(
        onClick = {},
        isEnabled = true,
        isLoading = true,
        iconDrawable = ImageVector.vectorResource(id = R.drawable.note_add_icon),
        iconDescription = "Add",
    )
}
