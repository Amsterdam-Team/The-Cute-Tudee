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




