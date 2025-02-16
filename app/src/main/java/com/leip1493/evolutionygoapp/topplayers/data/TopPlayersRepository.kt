package com.leip1493.evolutionygoapp.topplayers.data

import android.util.Log
import com.leip1493.evolutionygoapp.core.api.ApiClient
import com.leip1493.evolutionygoapp.core.api.dto.PlayerDTO
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.core.models.Season
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToLong

class TopPlayersRepository @Inject constructor(
    private val apiClient: ApiClient,
) {

    suspend fun getPlayers(season: Season, banlist: String): List<Player> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getPlayersStats(1, 20, banlist, season.id)
                Log.d("TopPlayersRepository", "Response: $response")

                if (response.isSuccessful) {
                    response.body()?.map { it.mapToPlayer() } ?: listOf()
                } else {
                    Log.d("TopPlayersRepository", "Error: ${response.errorBody()?.string()}")
                    emptyList()
                }

            } catch (e: Exception) {
                Log.d("TopPlayersRepository", "Error: $e")
                emptyList()
            }
        }
    }

    suspend fun getSeasons(): List<Season> {
        return listOf(
            Season(1, "Season 1"),
            Season(2, "Season 2"),
            Season(3, "Season 3"),
            Season(4, "Season 4"),
        ).reversed()
    }

    suspend fun getBanlist(): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getBanlist()
                if (!response.isSuccessful) {
                    return@withContext listOf()
                }

                response.body() ?: listOf()
            } catch (e: Exception) {
                Log.d("TopPlayersRepository", "Error: $e")
                listOf()
            }

        }
    }
}

private fun PlayerDTO.mapToPlayer(): Player {
    val stars = when {
        this.winRate >= 90 -> 5
        this.winRate >= 70 -> 4
        this.winRate >= 50 -> 3
        this.winRate >= 30 -> 2
        else -> 1
    }

    return Player(
        this.id,
        this.username.trim(),
        this.points,
        this.wins,
        this.losses,
        String.format(Locale.getDefault(), "%.2f", this.winRate) + "%",
        this.position.toInt(),
        stars,
    )
}