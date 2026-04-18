package com.anilkumar.realtimeleaderboard.leaderboard.model

data class RankedPlayer(
    val playerId: String,
    val username: String,
    val score: Int,
    val rank: Int,
    val previousRank: Int? = null,
    val rankChangeDirection: RankChangeDirection = RankChangeDirection.NONE,
    val isRecentlyUpdated: Boolean = false
)
