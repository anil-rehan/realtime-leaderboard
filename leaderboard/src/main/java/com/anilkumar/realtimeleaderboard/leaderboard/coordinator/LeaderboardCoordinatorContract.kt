package com.anilkumar.realtimeleaderboard.leaderboard.coordinator

import com.anilkumar.realtimeleaderboard.leaderboard.model.LeaderboardSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface LeaderboardCoordinatorContract {

    val snapshot: StateFlow<LeaderboardSnapshot>
    fun start(scope: CoroutineScope)
}