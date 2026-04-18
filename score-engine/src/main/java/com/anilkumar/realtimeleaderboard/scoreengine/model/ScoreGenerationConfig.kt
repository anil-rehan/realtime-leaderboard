package com.anilkumar.realtimeleaderboard.scoreengine.model

data class ScoreGenerationConfig(
    val players: List<Player>,
    val seed: Long,
    val minDelayMillis: Long,
    val maxDelayMillis: Long,
    val minScoreDelta: Int,
    val maxScoreDelta: Int

)
