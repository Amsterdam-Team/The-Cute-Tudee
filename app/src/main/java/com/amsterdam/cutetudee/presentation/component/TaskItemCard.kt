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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.chip.DateChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.imageModel
import kotlin.math.roundToInt

@Composable
fun TaskItemCard(
    categoryImage: Uri,
    modifier: Modifier = Modifier,
    showDate: Boolean = false,
    priorityUi: PriorityUi = PriorityUi.LOW,
    title: String = "",
    description: String = "",
    date: String = "",
    shape: Shape = RoundedCornerShape(16.dp),
    height: Dp = 121.dp,
    isDeletable: Boolean = false,
    onDeleteAction: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val maxOffsetPx = with(LocalDensity.current) { -56.dp.toPx() }
    val defaultOffset = 0f
    var draggedOffsetX by remember { mutableFloatStateOf(0f) }
    val state = rememberDraggableState { delta ->
        draggedOffsetX = (draggedOffsetX + delta * 1.75f).coerceIn(maxOffsetPx, defaultOffset)
    }
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDeletable) draggedOffsetX else defaultOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
        ),
    )

    Box(modifier.wrapContentHeight(), contentAlignment = Alignment.Center) {
        DeleteIcon(height, shape, onDeleteAction)
        Column(
            modifier = Modifier
                .height(height)
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = state,
                    onDragStarted = {
                        draggedOffsetX = maxOffsetPx
                    },
                    onDragStopped = {
                        draggedOffsetX =
                            if (draggedOffsetX < maxOffsetPx / 2) maxOffsetPx else defaultOffset
                    },
                    reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl,
                )
                .background(
                    color = AppTheme.color.surfaceHigh,
                    shape = shape,
                )
                .clip(shape)
                .clickable(
                    onClick = {
                        draggedOffsetX = defaultOffset
                        onClick()
                    },
                    role = Role.Button,
                )
                .padding(start = 4.dp, top = 4.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            TaskItemHeader(
                showDate = showDate,
                date = date,
                categoryImage = categoryImage,
                priorityUi = priorityUi,
            )
            TaskItemInfo(
                title = title,
                description = description,
                showDescription = description.isNotEmpty(),
            )
        }
    }
}

@Composable
private fun DeleteIcon(
    height: Dp,
    shape: Shape,
    onDeleteAction: () -> Unit
) {
    Box(
        modifier =
            Modifier
                .height(height)
                .fillMaxWidth()
                .background(
                    color = AppTheme.color.errorVariant,
                    shape = shape,
                )
                .padding(horizontal = 12.dp, vertical = 41.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.delete_icon),
            tint = AppTheme.color.error,
            contentDescription = "delete icon",
            modifier =
                Modifier
                    .align(Alignment.CenterEnd)
                    .clip(shape)
                    .clickable(
                        onClick = onDeleteAction,
                        role = Role.Button,
                    ),
        )
    }
}


@Composable
private fun TaskItemHeader(
    date: String,
    categoryImage: Uri,
    priorityUi: PriorityUi,
    showDate: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(56.dp)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageModel(context, categoryImage),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
        Spacer(Modifier.weight(1f))
        AnimatedVisibility(showDate) {
            DateChip(date)
        }
        PriorityChip(priorityUi = priorityUi, isSelected = true)
    }
}

@Composable
private fun TaskItemInfo(
    title: String,
    description: String,
    showDescription: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = title,
            color = AppTheme.color.body,
            style = AppTheme.textStyle.label.large,
        )
        AnimatedVisibility(showDescription) {
            Text(
                text = description,
                color = AppTheme.color.hint,
                style = AppTheme.textStyle.label.small,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TaskCardPreview() {
    var deleted by remember { mutableStateOf(false) }
    CuteTudeeTheme(isSystemInDarkTheme()) {
        TaskItemCard(
            categoryImage = Uri.EMPTY,
            showDate = false,
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