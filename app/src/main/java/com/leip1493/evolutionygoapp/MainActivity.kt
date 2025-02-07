package com.leip1493.evolutionygoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.leip1493.evolutionygoapp.core.navigation.NavigationWrapper
import com.leip1493.evolutionygoapp.ui.theme.EvolutionYGOAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EvolutionYGOAppTheme {
                NavigationWrapper()
            }
        }
    }
}
