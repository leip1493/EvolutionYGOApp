package com.leip1493.evolutionygoapp.core.models

data class Player(
    val userId: String,
    val name: String,
    val points: Int,
    val wins: Int,
    val losses: Int,
    val winRate: String,
    val position: Int,
    val stars: Int,
    val achievements: List<Achievement>,
)