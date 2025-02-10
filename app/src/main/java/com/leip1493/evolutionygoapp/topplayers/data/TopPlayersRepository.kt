package com.leip1493.evolutionygoapp.topplayers.data

import android.util.Log
import com.leip1493.evolutionygoapp.core.api.ApiClient
import com.leip1493.evolutionygoapp.core.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopPlayersRepository @Inject constructor(
    private val apiClient: ApiClient,
) {

    suspend fun getPlayers(season: Int, banlist: String): List<Player> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getPlayersStats(1, 20, banlist, season)
                Log.d("TopPlayersRepository", "Response: $response")

                if (response.isSuccessful) {
                    response.body()?.map {
                        Player(
                            it.username.trim(),
                            it.points,
                            it.wins,
                            it.losses,
                            it.winRate.toInt().toString(),
                            it.position.toInt()
                        )
                    } ?: listOf()
                } else {
                    Log.d("TopPlayersRepository", "Error: ${response.errorBody()?.string()}")
                    listOf()
                }

            } catch (e: Exception) {
                Log.d("TopPlayersRepository", "Error: $e")
                listOf()
            }
        }
    }

    suspend fun getSeasons(): List<String> {
        return listOf("Season 1", "Season 2", "Season 3", "Season 4").reversed()
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