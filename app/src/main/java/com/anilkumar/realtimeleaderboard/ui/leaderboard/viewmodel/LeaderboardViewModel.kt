package com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anilkumar.realtimeleaderboard.leaderboard.coordinator.LeaderboardCoordinatorContract
import com.anilkumar.realtimeleaderboard.ui.leaderboard.model.LeaderboardRowUiModel
import com.anilkumar.realtimeleaderboard.ui.leaderboard.model.LeaderboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LeaderboardViewModel(
    private val coordinator: LeaderboardCoordinatorContract
) : ViewModel() {

    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

    init {
        coordinator.start(viewModelScope)

        coordinator.snapshot
            .onEach { snapshot ->
                _uiState.value = _uiState.value.copy(
                    entries = snapshot.players.map { rankedPlayer ->
                        LeaderboardRowUiModel(
                            playerId = rankedPlayer.playerId,
                            username = rankedPlayer.username,
                            score = rankedPlayer.score,
                            rank = rankedPlayer.rank,
                            rankChangeDirection = rankedPlayer.rankChangeDirection,
                            isRecentlyUpdated = rankedPlayer.isRecentlyUpdated
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}