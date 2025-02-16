package com.leip1493.evolutionygoapp.playerdetail.data

import com.leip1493.evolutionygoapp.core.api.ApiClient
import com.leip1493.evolutionygoapp.core.api.dto.MatchDTO
import com.leip1493.evolutionygoapp.core.api.dto.PlayerDTO
import com.leip1493.evolutionygoapp.core.models.Match
import com.leip1493.evolutionygoapp.core.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class PlayerDetailRepository @Inject constructor(
    private val apiClient: ApiClient,
) {
    suspend fun getPlayerStats(playerId: String, season: String, banlist: String): Player {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getPlayerStats(playerId, banlist, season.toInt())
            if (response.isSuccessful) {
                val playerDto = response.body() as PlayerDTO
                playerDto.mapToPlayer()
            } else {
                Player(
                    "fake-id",
                    "Fake user",
                    0,
                    0,
                    0,
                    "0",
                    0,
                    1
                )
            }
        }
    }

    suspend fun getPlayerMatches(
        page: Int,
        limit: Int,
        playerId: String,
        season: String,
        banlist: String,
    ): List<Match> {
        return withContext(Dispatchers.IO) {
            val filteredBanlist = if (banlist == "Global") null else banlist
            val response =
                apiClient.getPlayerMatches(playerId, page, limit, season.toInt(), filteredBanlist)
            if (!response.isSuccessful) {
                return@withContext emptyList()
            }
            response.body()?.map { it.mapToMatch() } ?: emptyList()
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

private fun MatchDTO.mapToMatch(): Match {
    val matchDto = this

    val duelMode =
        "${matchDto.playerNames.size} vs ${matchDto.opponentNames.size} | Best of ${matchDto.bestOf}"

    val type = if (matchDto.playerNames.size > 1) "Tag" else "PvP"

    val result = "${matchDto.playerScore}-${matchDto.opponentScore}"

    val date = matchDto.date.split("T").first()

    return Match(
        matchDto.season.toString(),
        duelMode,
        type,
        matchDto.banListName,
        matchDto.playerNames,
        matchDto.opponentNames,
        result,
        matchDto.points,
        date
    )
}

private fun getMockMatches(): List<Match> {
    val dtoMatchList = listOf(
        MatchDTO(
            "0910bbfe-7424-4d66-bbbd-6e78c8dd7cdb",
            3,
            "N/A",
            listOf("TheGhost9103", "Pepito"),
            listOf(
                "JOrdan~(GorZ 2025~♠",
                "SAmuel~(GorZ 2025~♠"
            ),
            2,
            1,
            1,
            true,
            "2025-02-13T20:04:49.752Z",
            4
        ),
        MatchDTO(
            "0910bbfe-7424-4d66-bbbd-6e78c8dd7cdb",
            3,
            "N/A",
            listOf("TheGhost9103"),
            listOf("vector"),
            0,
            2,
            -1,
            true,
            "2025-02-13T20:04:49.752Z",
            4
        ),
        MatchDTO(
            "0910bbfe-7424-4d66-bbbd-6e78c8dd7cdb",
            3,
            "N/A",
            listOf("TheGhost9103"),
            listOf("vector"),
            2,
            1,
            1,
            true,
            "2025-02-13T20:04:49.752Z",
            4
        ),
        MatchDTO(
            "0910bbfe-7424-4d66-bbbd-6e78c8dd7cdb",
            3,
            "N/A",
            listOf("TheGhost9103"),
            listOf("vector"),
            2,
            1,
            1,
            true,
            "2025-02-13T20:04:49.752Z",
            4
        )
    )
    return dtoMatchList.map { it.mapToMatch() }
}

