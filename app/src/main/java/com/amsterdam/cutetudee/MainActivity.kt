package com.amsterdam.cutetudee

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amsterdam.cutetudee.presentation.CuteTudeeApp

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
