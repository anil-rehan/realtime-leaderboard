package com.anilkumar.realtimeleaderboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

import com.anilkumar.realtimeleaderboard.core.AppContainer

import com.anilkumar.realtimeleaderboard.ui.leaderboard.screen.LeaderboardScreen
import com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel.LeaderboardViewModel
import com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel.LeaderboardViewModelFactory

class MainActivity : ComponentActivity() {

    private val appContainer = AppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: LeaderboardViewModel = viewModel(
                factory = LeaderboardViewModelFactory(appContainer.leaderboardCoordinator)
            )

            LeaderboardScreen(viewModel = viewModel)
        }


    }
}