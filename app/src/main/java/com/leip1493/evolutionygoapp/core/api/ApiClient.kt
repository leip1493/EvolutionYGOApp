package com.leip1493.evolutionygoapp.core.api

import com.leip1493.evolutionygoapp.core.api.dto.PlayerDTO
import com.leip1493.evolutionygoapp.core.api.response.GetPlayerStatsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET("api/v1/stats?page=1&limit=20&banListName=Global&season=4")
    suspend fun getPlayersStats(): Response<List<PlayerDTO>>

    //        @Query("user") user: String,
//        @Query("user") password: String,
}