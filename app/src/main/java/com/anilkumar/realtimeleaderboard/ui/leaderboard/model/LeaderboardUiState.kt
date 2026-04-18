package com.anilkumar.realtimeleaderboard.ui.leaderboard.model

data class LeaderboardUiState(
    val seasonTitle: String = "GENESIS SEASON",
    val seasonEndsText: String = "Season ends in 60 days",
    val currentUserRank: String = "718th",
    val currentUserScore: Int = 2100,
    val entries: List<LeaderboardRowUiModel> = emptyList(),
    val isLoading: Boolean = false
)