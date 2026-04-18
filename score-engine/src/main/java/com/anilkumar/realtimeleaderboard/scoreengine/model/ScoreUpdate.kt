package com.anilkumar.realtimeleaderboard.scoreengine.model

data class ScoreUpdate(
    val playerId: String,
    val newScore: Int,
    val scoreDelta: Int,
    val emittedAtMillis: Long

)
