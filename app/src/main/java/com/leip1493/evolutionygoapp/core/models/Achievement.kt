package com.leip1493.evolutionygoapp.core.models

// Modelos de datos
data class Achievement(
    val id: Int,
    val icon: String,
    val name: String,
    val labels: List<String>,
    val unlockedAt: String,
    val description: String,
    val earnedPoints: Int,
)