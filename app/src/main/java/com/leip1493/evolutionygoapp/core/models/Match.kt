package com.leip1493.evolutionygoapp.core.models

data class Match(
    val season: String,
    val duelMode: String,
    val type: String,
    val banlist: String,
    val playerNames: List<String>,
    val opponentNames: List<String>,
    val result: String,
    val points: Int,
    val date: String,
)