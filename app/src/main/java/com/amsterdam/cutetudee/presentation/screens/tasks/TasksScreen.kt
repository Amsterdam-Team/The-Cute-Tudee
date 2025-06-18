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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.theme.AppTheme


@Composable
fun TasksScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Center) {
        Text("Tasks Screen", color = AppTheme.color.title)
    }
}


@Composable
fun TasksContent(
    modifier: Modifier = Modifier
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
            dateText = "Jun, 2025",
            dateOfDay = 14,
            dayName = "Sun"
        )

    }

}


@Preview(showBackground = true)
@Composable
private fun TaskContentPreview() {
    TasksContent()
}

@Composable
private fun DateContainer(
    modifier: Modifier = Modifier,
    dateText: String,
    dateOfDay: Int,
    dayName: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArrowContainer(arrowIcon = R.drawable.left_arrow_icon)
            DateTextContainer(dateText = dateText)
            ArrowContainer(arrowIcon = R.drawable.right_arrow_icon)
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(30) {
                DayContainer(
                    dateOfDay = dateOfDay,
                    day = dayName
                )
            }
        }
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
            painter = painterResource(arrowIcon),
            contentDescription = null,
            tint = AppTheme.color.body
        )
    }
}

@Composable
private fun DateTextContainer(
    modifier: Modifier = Modifier,
    dateText: String
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
private fun DayContainer(
    modifier: Modifier = Modifier,
    dateOfDay: Int,
    day: String
) {
    var isClicked by remember { mutableStateOf(false) }
    val bkColor = if (isClicked) AppTheme.color.surface else AppTheme.color.primary

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = bkColor)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { isClicked = !isClicked },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            text = "$dateOfDay",
            style = AppTheme.textStyle.title.medium,
            color = AppTheme.color.body
        )
        Text(
            text = day,
            style = AppTheme.textStyle.body.small,
            color = AppTheme.color.hint
        )
    }
}
