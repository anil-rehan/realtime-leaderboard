package com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anilkumar.realtimeleaderboard.leaderboard.coordinator.LeaderboardCoordinator

class LeaderboardViewModelFactory (
    private val coordinator: LeaderboardCoordinator
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LeaderboardViewModel(coordinator) as T
    }
}