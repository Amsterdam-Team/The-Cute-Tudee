package com.amsterdam.cutetudee.presentation

import android.content.pm.ActivityInfo
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.colors.darkThemeColors
import com.amsterdam.cutetudee.presentation.theme.colors.lightThemeColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableScreenOrientation()
        enableEdgeToEdge()
        setContent {
            CuteTudeeApp()
        }
    }

    private fun enableScreenOrientation() {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }
}
