package com.amsterdam.cutetudee.presentation.screens.tasks


import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.getCurrentMonthDays
import com.amsterdam.cutetudee.presentation.utils.getNumberOfDays
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle


@Composable
fun TasksScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit,
    viewModel: TasksViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    TasksContent(
        tasksUiState = state, onTabChange = viewModel::filteredTasksByStatus
    )
}
@Composable
fun TasksContent(
    tasksUiState: TasksUiState,
    onTabChange: (TaskStatusUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(top = 40.dp)
                .fillMaxSize()
                .background(AppTheme.color.surfaceHigh)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                text = stringResource(R.string.tasks),
                style = AppTheme.textStyle.title.large,
                color = AppTheme.color.title
            )
            DateContainer(
                dateText = "${
                    tasksUiState.currentDate.month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.current.platformLocale
                    )
                }, ${tasksUiState.currentDate.year}",
                dateOfDay = tasksUiState.currentDate.dayOfMonth,
                daysOfMonth = getNumberOfDays()
            )
            TabsContent(
                selectedStatus = tasksUiState.currentSelectedTaskStatusUi,
                onTabChange = onTabChange,
            )
       TasksContainer(
           tasksUiState = tasksUiState,
       )
        }

        CustomFloatingActionButton(
            iconDrawable = painterResource(id = R.drawable.note_add_icon),
            onClick = {},
            isLoading = false,
            isEnabled = true,
            modifier = Modifier
                .padding(end = 12.dp, bottom = 10.dp)
                .align(Alignment.BottomEnd),
        )

    }
}

@Composable
private fun DayContainer(
    dateOfDay: Int,
    day: String,
    isClicked: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gradientColor = Brush.verticalGradient(
        listOf(
            AppTheme.color.primaryGradientStart,
            AppTheme.color.primaryGradientEnd,
        ),
    )
    val bkColor: Brush = if (isClicked) {
        gradientColor
    } else {
        Brush.verticalGradient(listOf(AppTheme.color.surface, AppTheme.color.surface))
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onSelected)
            .background(brush = bkColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val dateOfDayColor = if (isClicked) AppTheme.color.onPrimary else AppTheme.color.body
        val dayColor = if (isClicked) AppTheme.color.onPrimaryCaption else AppTheme.color.hint

        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            text = "$dateOfDay",
            style = AppTheme.textStyle.title.medium,
            color = dateOfDayColor
        )
        Text(
            text = day,
            style = AppTheme.textStyle.body.small,
            color = dayColor
        )
    }
}

@Composable
private fun DateTextContainer(
    dateText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 91.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = dateText,
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body
        )
        Icon(
            modifier = Modifier.rotate(270f),
            painter = painterResource(R.drawable.left_arrow_icon),
            contentDescription = null,
            tint = AppTheme.color.body
        )
    }
}

@Composable
private fun ArrowContainer(
    modifier: Modifier = Modifier,
    @DrawableRes arrowIcon: Int
) {
    Row(
        modifier = modifier
            .size(32.dp)
            .border(
                width = 1.dp, shape = CircleShape,
                color = AppTheme.color.stroke
            ), horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(arrowIcon),
            contentDescription = null,
            tint = AppTheme.color.body
        )
    }
}

@Composable
private fun DateContainer(
    modifier: Modifier = Modifier,
    dateText: String,
    dateOfDay: Int,
    daysOfMonth: List<Int>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.surfaceHigh)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArrowContainer(arrowIcon = R.drawable.left_arrow_icon)
            DateTextContainer(dateText = dateText)
            ArrowContainer(arrowIcon = R.drawable.right_arrow_icon)
        }

        var currentSelected by remember { mutableIntStateOf(dateOfDay) }
        LazyRow(
            state = rememberLazyListState(initialFirstVisibleItemIndex = currentSelected.minus(1)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(daysOfMonth) { item ->
                DayContainer(
                    dateOfDay = item,
                    day = getCurrentMonthDays(item),
                    isClicked = currentSelected == item,
                    onSelected = { currentSelected = item },
                )
            }
        }
    }
}
@Composable
private fun TabsContent(
    selectedStatus: TaskStatusUi,
    onTabChange: (TaskStatusUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableStateOf(selectedStatus.ordinal) }
    val borderColor = AppTheme.color.stroke
    val tabs = TaskStatusUi.entries

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            },
        indicator = @Composable { tabPositions ->
            val currentTabPosition = tabPositions[selectedTabIndex]
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(currentTabPosition)
                    .height(4.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(AppTheme.color.secondary)
            )
        },
        containerColor = AppTheme.color.surfaceHigh,
    ) {
        tabs.forEachIndexed { index, status ->
            val isSelected = selectedTabIndex == index
            val titleColor = if (isSelected) AppTheme.color.title else AppTheme.color.hint
            val titleStyle =
                if (isSelected) AppTheme.textStyle.title.medium else AppTheme.textStyle.label.small
            Tab(
                selected = isSelected,
                onClick = {
                    selectedTabIndex = index
                    onTabChange(status)
                }
            ) {
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = status.name,
                        style = titleStyle,
                        color = titleColor,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    if (isSelected) {
                        NotificationBadge("23")
                    }
                }
            }
        }
    }
}


@Composable
private fun TaskItem(
    @DrawableRes taskIcon: Int,
    priorityUi: PriorityUi,
    taskName: String,
    taskDescription: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.color.surfaceHigh)
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 12.dp, start = 4.dp, end = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(56.dp),
                painter = painterResource(taskIcon),
                contentDescription = null,
                tint = AppTheme.color.pinkAccent,
            )
            PriorityChip(
                priorityUi = priorityUi,
                isSelected = true,
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            text = taskName,
            style = AppTheme.textStyle.label.large,
            color = AppTheme.color.body
        )
        taskDescription?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                style = AppTheme.textStyle.label.small,
                color = AppTheme.color.hint
            )
        }


    }
}


@Composable
private fun NotificationBadge(
    badgeCount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(start = 4.dp)
            .size(28.dp)
            .clip(CircleShape)
            .background(AppTheme.color.surface),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = badgeCount,
            style = AppTheme.textStyle.label.medium,
            color = AppTheme.color.body
        )

    }
}

@Composable
private fun  TasksContainer(
    tasksUiState: TasksUiState,
    modifier: Modifier= Modifier
){
    LazyColumn(
        modifier = modifier.background(AppTheme.color.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(tasksUiState.tasks) { task ->
            TaskItem(
                taskIcon = R.drawable.chef_icon,
                priorityUi = PriorityUi.MEDIUM,
                taskName = task.title,
                taskDescription = task.description
            )
        }
    }
}
@ThemeAndLocalePreviews
@Composable
private fun TaskContentPreview() {
    TasksContent(
        tasksUiState = TasksUiState(),
        onTabChange = TODO(),

        )
}