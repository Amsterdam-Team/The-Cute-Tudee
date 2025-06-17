package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun TudeeFloatingActionButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    iconDrawable: Painter,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    iconDescription: String? = null,
) {
    val buttonModifier =
        if (!isEnabled) {
            Modifier
                .clip(CircleShape)
                .size(64.dp)
                .background(AppTheme.color.disable)
        } else {
            Modifier
                .dropShadow(CircleShape, color = Color.Black.copy(0.12f))
                .clip(CircleShape)
                .size(64.dp)
                .background(
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

    IconButton(
        onClick = onClick,
        enabled = isEnabled,
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = contentColor,
            ),
        modifier =
            modifier
                .then(buttonModifier),
    ) {
        if (isLoading && isEnabled) {
            CustomAnimatedProgressIndicator(
                tint = contentColor,
            )
        } else {
            Icon(
                painter = iconDrawable,
                contentDescription = iconDescription,
            )
        }
    }
}

@Preview(name = "TudeeFloatingActionButton", showBackground = true)
@Composable
private fun PreviewTudeeFloatingActionButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
    ) {
        TudeeFloatingActionButton(
            iconDrawable = painterResource(id = R.drawable.note_add_icon),
            onClick = {},
            isLoading = true,
            isEnabled = false,
            modifier = Modifier,
        )
    }
}
