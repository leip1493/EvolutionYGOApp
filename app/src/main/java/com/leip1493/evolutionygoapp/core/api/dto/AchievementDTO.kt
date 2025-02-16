package com.leip1493.evolutionygoapp.core.api.dto

data class AchievementDTO(
    val id: Int,
    val icon: String,
    val name: String,
    val labels: List<String>,
    val unlockedAt: String,
    val description: String,
    val earnedPoints: Int,
)