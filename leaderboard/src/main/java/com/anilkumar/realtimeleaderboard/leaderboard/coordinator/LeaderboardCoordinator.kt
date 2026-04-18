package com.anilkumar.realtimeleaderboard.leaderboard.coordinator

import com.anilkumar.realtimeleaderboard.leaderboard.domain.RankingEngine
import com.anilkumar.realtimeleaderboard.leaderboard.model.LeaderboardSnapshot
import com.anilkumar.realtimeleaderboard.leaderboard.repository.LeaderboardRepository
import com.anilkumar.realtimeleaderboard.scoreengine.api.ScoreEventSource
import com.anilkumar.realtimeleaderboard.scoreengine.model.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaderboardCoordinator(
    private val players: List<Player>,
    private val scoreEventSource: ScoreEventSource,
    private val repository: LeaderboardRepository,
    private val rankingEngine: RankingEngine
) : LeaderboardCoordinatorContract {

    private val _snapshot = MutableStateFlow(LeaderboardSnapshot(players = emptyList()))
    override val snapshot: StateFlow<LeaderboardSnapshot> = _snapshot.asStateFlow()

    private var observeJob: Job? = null

    override fun start(scope: CoroutineScope) {
        if (observeJob != null) return

        repository.initialize(players)

        _snapshot.value = rankingEngine.buildSnapshot(
            currentScores = repository.getCurrentScores(),
            previousSnapshot = null,
            updatedPlayerId = null
        )

        observeJob = scope.launch {
            scoreEventSource.scoreUpdates().collect { update ->
                repository.applyScoreUpdate(update)

                _snapshot.value = rankingEngine.buildSnapshot(
                    currentScores = repository.getCurrentScores(),
                    previousSnapshot = _snapshot.value,
                    updatedPlayerId = update.playerId
                )
            }
        }
    }
}

