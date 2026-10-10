package com.nothingtube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nothingtube.ui.navigation.AppNavigation
import com.nothingtube.ui.theme.NothingTubeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NothingTubeTheme {
                AppNavigation()
            }
        }
    }
}
