package com.leip1493.evolutionygoapp.topplayers.data

import com.leip1493.evolutionygoapp.core.models.Player
import javax.inject.Inject

class TopPlayersRepository @Inject constructor() {

    suspend fun getPlayers(): List<Player> {
        return listOf(
            Player(
                "Akane", 999, 999, 0, "75.05%", 1
            ),
            Player(
                "TheGhost9103", 800, 999, 0, "70.00%", 2
            ),
            Player("Ene", 700, 999, 0, "65.51%", 3),
            Player("Ene", 659, 999, 0, "50.05%", 4),
            Player("Lorem", 659, 999, 0, "50.05%", 5),
            Player("Ipsum", 659, 999, 0, "50.05%", 6),
            Player("Foo", 659, 999, 0, "50.05%", 7),
            Player("Bar", 659, 999, 0, "50.05%", 8),
            Player("Yugi", 659, 999, 0, "50.05%", 9),
            Player("Kaiba", 659, 999, 0, "50.05%", 10),
            Player("Joey", 659, 999, 0, "50.05%", 11),
        ).sortedBy { it.position }
    }

    suspend fun getSeasons(): List<String> {
        return listOf("Season 1", "Season 2", "Season 3", "Season 4").reversed()
    }

    suspend fun getBanlist(): List<String> {
        return listOf("Banlist 1", "Banlist 2", "Banlist 3").reversed()
    }
}