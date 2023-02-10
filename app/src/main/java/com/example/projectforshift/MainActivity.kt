package com.example.projectforshift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.projectforshift.navigation.SetupNavHost
import com.example.projectforshift.ui.theme.ProjectForShiftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectForShiftTheme {
                val navController = rememberNavController()
                SetupNavHost(navController = navController)

            }
        }
    }
}
