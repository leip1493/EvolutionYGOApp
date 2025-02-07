package com.leip1493.evolutionygoapp.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Text("Hola que hace")
        }
    }
}