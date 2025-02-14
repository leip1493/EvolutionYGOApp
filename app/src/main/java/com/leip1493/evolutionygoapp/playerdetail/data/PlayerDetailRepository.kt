package com.leip1493.evolutionygoapp.playerdetail.data

import com.leip1493.evolutionygoapp.core.api.ApiClient
import com.leip1493.evolutionygoapp.core.api.dto.PlayerDTO
import com.leip1493.evolutionygoapp.core.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerDetailRepository @Inject constructor(
    private val apiClient: ApiClient,
) {
    suspend fun getPlayerStats(playerId: String, season: String, banlist: String): Player {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getPlayerStats(playerId, banlist, season.toInt())
            if (response.isSuccessful) {
                val body = response.body() as PlayerDTO
                Player(
                    body.id,
                    body.username.trim(),
                    body.points,
                    body.wins,
                    body.losses,
                    body.winRate.toInt().toString(),
                    body.position.toInt()
                )
            } else {
                Player(
                    "fake-id",
                    "Fake user",
                    0,
                    0,
                    0,
                    "0",
                    0
                )
            }
        }
    }
}