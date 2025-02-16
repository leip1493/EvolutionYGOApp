package com.leip1493.evolutionygoapp.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object TopPlayers

@Serializable
data class PlayerDetail(val id: String, val season: String, val banlist: String)