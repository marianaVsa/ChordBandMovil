package com.example.chorband

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.chorband.ui.navigation.AppNavigation
import com.example.chorband.ui.theme.ChorBandTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChorBandTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}