package com.leip1493.evolutionygoapp.core.api

import com.leip1493.evolutionygoapp.core.api.dto.MatchDTO
import com.leip1493.evolutionygoapp.core.api.dto.PlayerDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("api/v1/stats")
    suspend fun getPlayersStats(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("banListName") banListName: String,
        @Query("season") season: Int,
    ): Response<List<PlayerDTO>>

    @GET("api/v1/ban-lists/")
    suspend fun getBanlist(): Response<List<String>>

    @GET("api/v1/users/{userId}/stats")
    suspend fun getPlayerStats(
        @Path("userId") userId: String,
        @Query("banListName") banListName: String,
        @Query("season") season: Int,
    ): Response<PlayerDTO>

    @GET("api/v1/users/{userId}/matches")
    suspend fun getPlayerMatches(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("season") season: Int,
        @Query("banListName") banListName: String?,
    ): Response<List<MatchDTO>>
}