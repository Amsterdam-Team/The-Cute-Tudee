package com.amsterdam.cutetudee.presentation.component.task_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.chip.DateChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import kotlin.math.roundToInt

@Composable
fun TaskItemCard(
    taskItemUiState: TaskItemUiState,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.color.errorVariant, shape = shape
                )
                .padding(horizontal = 12.dp, vertical = 41.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_icon),
                tint = AppTheme.color.error,
                contentDescription = "delete icon",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(shape)
                    .clickable(
                        onClick = onDeleteAction, role = Role.Button
                    )
            )
        }

        Column(
            modifier = Modifier
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
                    reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl
                )
                .background(
                    color = AppTheme.color.surfaceHigh, shape = shape
                )
                .clip(shape)
                .clickable(
                    onClick = {
                        draggedOffsetX = defaultOffset
                        onClick()
                    }, role = Role.Button
                )
                .padding(
                    start = 4.dp, top = 4.dp, end = 12.dp, bottom = 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            TaskItemHeader(
                taskItemUiState = taskItemUiState, showDate = taskItemUiState.date.isNotEmpty()
            )
            TaskItemInfo(
                taskItemUiState = taskItemUiState,
                showDescription = taskItemUiState.description.isNotEmpty()
            )
        }
    }
}


@Composable
private fun TaskItemHeader(
    taskItemUiState: TaskItemUiState,
    showDate: Boolean,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = taskItemUiState.categoryImage,
                contentDescription = null,
                modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(Modifier.weight(1f))
        AnimatedVisibility(showDate) {
            DateChip(taskItemUiState.date)
        }
        PriorityChip(
            priorityUi = taskItemUiState.priorityUi, isSelected = true
        )
    }
}

@Composable
private fun TaskItemInfo(
    taskItemUiState: TaskItemUiState, showDescription: Boolean, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = taskItemUiState.name,
            color = AppTheme.color.body,
            style = AppTheme.textStyle.label.large
        )
        AnimatedVisibility(showDescription) {
            Text(
                text = taskItemUiState.description,
                color = AppTheme.color.hint,
                style = AppTheme.textStyle.label.small,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
            taskItemUiState = TaskItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                priorityUi = PriorityUi.MEDIUM,
                name = stringResource(R.string.empty_screen_title),
                description = stringResource(R.string.empty_screen_description),
                date = "12-03-2025",
            ),
            isDeletable = true,
            onDeleteAction = {
                deleted = !deleted
            },
            onClick = {
                deleted = !deleted
            }, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        AnimatedVisibility(deleted) {
            Box(modifier = Modifier.fillMaxSize())
        }

    }
}