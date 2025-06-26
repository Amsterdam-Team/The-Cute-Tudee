package com.amsterdam.cutetudee.presentation.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.theme.LocalIsDarkTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animation.scale
import com.amsterdam.cutetudee.presentation.utils.imageModel
import kotlin.math.roundToInt

@Composable
fun TaskItemCard(
    categoryImage: Uri,
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    priorityUi: PriorityUi = PriorityUi.LOW,
    isDeletable: Boolean = false,
    onDeleteAction: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val maxOffsetPx = with(LocalDensity.current) { -56.dp.toPx() }
    val defaultOffset = 0f
    var draggedOffsetX by remember { mutableFloatStateOf(defaultOffset) }
    val state =
        rememberDraggableState { delta ->
            draggedOffsetX = (draggedOffsetX + delta * 1.75f).coerceIn(maxOffsetPx, defaultOffset)
        }
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDeletable) draggedOffsetX else defaultOffset,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
    )

    val mainCardModifier =
        if (isDeletable) {
            Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = state,
                    onDragStarted = {
                        if (maxOffsetPx > 0) draggedOffsetX = maxOffsetPx
                    },
                    onDragStopped = {
                        draggedOffsetX =
                            if (draggedOffsetX < maxOffsetPx / 2) maxOffsetPx else defaultOffset
                    },
                    reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl,
                )
        } else {
            Modifier
        }
    val cardShape = RoundedCornerShape(16.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        DeleteIcon(
            shape = cardShape,
            onDeleteAction = {
                onDeleteAction()
                draggedOffsetX = defaultOffset
            },
            modifier = Modifier.matchParentSize(),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier =
                mainCardModifier
                    .clip(cardShape)
                    .background(AppTheme.color.surfaceHigh)
                    .clickable(
                        onClick = {
                            if (isDeletable) {
                                draggedOffsetX = defaultOffset
                            }
                            onClick()
                        },
                        role = Role.Button,
                    )
                    .padding(start = 4.dp, top = 4.dp, end = 12.dp, bottom = 12.dp),
        ) {
            TaskItemHeader(
                categoryImage = categoryImage,
                priorityUi = priorityUi,
            )
            TaskItemInfo(
                title = title,
                description = description,
            )
        }
    }
}

@Composable
private fun DeleteIcon(
    shape: Shape,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = AppTheme.color.errorVariant,
                    shape = shape,
                )
                .clip(shape)
                .clickable(
                    remember { MutableInteractionSource() },
                    ripple(),
                    onClick = onDeleteAction,
                )
                .padding(horizontal = 12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.delete_icon),
            tint = AppTheme.color.error,
            contentDescription = "delete icon",
            modifier =
                Modifier
                    .align(Alignment.CenterEnd),
        )
    }
}

@Composable
private fun TaskItemHeader(
    categoryImage: Uri,
    priorityUi: PriorityUi,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .size(56.dp)
                    .padding(12.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (LocalIsDarkTheme.current)
                AsyncImage(
                    model = imageModel(context, categoryImage),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .scale(0.5f)
                        .blur(20.dp, BlurredEdgeTreatment.Unbounded)
                        .alpha(0.3f)
                )
            AsyncImage(
                model = imageModel(context, categoryImage),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
        PriorityChip(
            priorityUi = priorityUi,
            isSelected = true,
        )
    }
}

@Composable
private fun TaskItemInfo(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
    ) {
        Text(
            text = title,
            color = AppTheme.color.body,
            style = AppTheme.textStyle.label.large,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = description,
            color = AppTheme.color.hint,
            style = AppTheme.textStyle.label.small,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TaskCardPreview() {
    var deleted by remember { mutableStateOf(false) }
    CuteTudeeTheme(isSystemInDarkTheme()) {
        TaskItemCard(
            categoryImage = Uri.EMPTY,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            priorityUi = PriorityUi.MEDIUM,
            title = stringResource(R.string.empty_screen_title),
            description = stringResource(R.string.empty_screen_description),
            isDeletable = true,
            onDeleteAction = {
                deleted = !deleted
            },
        ) {
            deleted = !deleted
        }

        AnimatedVisibility(deleted) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}
