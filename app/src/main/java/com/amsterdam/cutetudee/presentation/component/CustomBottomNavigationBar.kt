package com.amsterdam.cutetudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun CustomBottomNavigationBar(
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier
) {
    var selectedItem by rememberSaveable { mutableStateOf(items.first().route) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.surfaceHigh)
            .padding(vertical = 16.dp, horizontal = 32.dp)
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            val isSelected = selectedItem == item.route
            CustomNavigationItem(
                item = item,
                isSelected = isSelected,
                onClick = {
                    if (!isSelected) {
                        selectedItem = item.route
                        /// TODO("Will Navigate Here")
                    }
                }
            )
        }
    }
}

@Composable
private fun CustomNavigationItem(
    item: BottomNavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) AppTheme.color.primaryVariant else Color.Transparent
    val contentColor = if (isSelected) AppTheme.color.primary else AppTheme.color.hint
    val icon = if (isSelected) { item.activeIcon } else { item.icon }
    Image(
        painter = painterResource(icon),
        contentDescription = null,
        colorFilter = ColorFilter.tint(contentColor),
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(9.dp)
            .size(24.dp)
    )
}

data class BottomNavigationItem(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val activeIcon: Int
)

@Preview
@Composable
private fun CustomBottomNavigationBarPreview() {
    val navigationItems = listOf(
        BottomNavigationItem(
            route = "home",
            icon = R.drawable.home2_icon,
            activeIcon = R.drawable.home2_filled_icon
        ),
        BottomNavigationItem(
            route = "tasks",
            icon = R.drawable.note_icon,
            activeIcon = R.drawable.note_filled_icon
        ),
        BottomNavigationItem(
            route = "categories",
            icon = R.drawable.menu_circle_icon,
            activeIcon = R.drawable.menu_circle_filled_icon
        )
    )
    Box(modifier = Modifier.padding(vertical = 50.dp)) {
        CustomBottomNavigationBar(
            items = navigationItems
        )
    }
}