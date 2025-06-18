package com.amsterdam.cutetudee.presentation.navigation

import androidx.annotation.DrawableRes
import com.amsterdam.cutetudee.R

enum class NavigationBarItems(
    @DrawableRes val unSelectedIcon: Int, @DrawableRes val selectedIcon: Int, val screen: Screen
){
    Home(
        unSelectedIcon = R.drawable.home2_icon,
        selectedIcon = R.drawable.home2_filled_icon,
        screen = Screen.Home
    ),
    Tasks(
        unSelectedIcon = R.drawable.note_icon,
        selectedIcon = R.drawable.note_filled_icon,
        screen = Screen.Tasks()
    ),
    Categories(
        unSelectedIcon = R.drawable.menu_circle_icon,
        selectedIcon = R.drawable.menu_circle_filled_icon,
        screen = Screen.Categories
    )
}