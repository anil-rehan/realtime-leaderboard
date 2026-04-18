package com.anilkumar.realtimeleaderboard.leaderboard.model

data class LeaderboardSnapshot(
    val players: List<RankedPlayer>,
    val lastUpdatedPlayerId: String? = null,
    val generatedAtMillis: Long = System.currentTimeMillis()
)
