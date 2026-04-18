package com.anilkumar.realtimeleaderboard.ui.leaderboard.model

import com.anilkumar.realtimeleaderboard.leaderboard.model.RankChangeDirection

data class LeaderboardRowUiModel(
    val playerId: String,
    val username: String,
    val score: Int,
    val rank: Int,
    val rankChangeDirection: RankChangeDirection,
    val isRecentlyUpdated: Boolean
)