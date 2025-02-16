package com.leip1493.evolutionygoapp.core.api.dto

import com.google.gson.annotations.SerializedName

data class PlayerDTO(
    @SerializedName("userId") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("points") val points: Int,
    @SerializedName("wins") val wins: Int,
    @SerializedName("losses") val losses: Int,
    @SerializedName("winRate") val winRate: Float,
    @SerializedName("position") val position: String,
    @SerializedName("achievements") val achievements: List<AchievementDTO>,
)