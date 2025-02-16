package com.leip1493.evolutionygoapp.core.api.dto

data class MatchDTO(
    val userId: String,
    val bestOf: Int,
    val banListName: String,
    val playerNames: List<String>,
    val opponentNames: List<String>,
    val playerScore: Int,
    val opponentScore: Int,
    val points: Int,
    val winner: Boolean,
    val date: String,
    val season: Int,
)