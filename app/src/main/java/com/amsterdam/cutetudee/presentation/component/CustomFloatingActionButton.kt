package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun CustomFloatingActionButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    iconDrawable: Painter,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    iconDescription: String? = null,
) {
    val baseModifier = Modifier
        .clip(CircleShape)
        .size(64.dp)

    val buttonModifier =
        if (!isEnabled) {
            Modifier
                .then(baseModifier)
                .background(AppTheme.color.disable)
        } else {
            Modifier
                .dropShadow(CircleShape, color = AppTheme.color.dropShadowColor)
                .then(baseModifier)
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

    Box(
        modifier = modifier
            .then(buttonModifier)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 32.dp),
                onClick = onClick,
                enabled = isEnabled && !isLoading,
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading && isEnabled) {
            CustomAnimatedProgressIndicator(
                tint = contentColor,
            )
        } else {
            Icon(
                painter = iconDrawable,
                contentDescription = iconDescription,
                tint = contentColor
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TudeeFloatingActionButtonPreview() {
    CuteTudeeTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
        ) {
            CustomFloatingActionButton(
                iconDrawable = painterResource(id = R.drawable.note_add_icon),
                onClick = {},
                isLoading = false,
                isEnabled = true,
                modifier = Modifier,
            )
        }
    }
}
