package com.anilkumar.realtimeleaderboard.ui.leaderboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anilkumar.realtimeleaderboard.ui.leaderboard.components.LeaderboardHeader
import com.anilkumar.realtimeleaderboard.ui.leaderboard.components.LeaderboardRow
import com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel.LeaderboardViewModel


@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    LeaderboardHeader(
                        seasonTitle = uiState.seasonTitle,
                        seasonEndsText = uiState.seasonEndsText,
                        currentUserRank = uiState.currentUserRank,
                        currentUserScore = uiState.currentUserScore
                    )
                }

                item {
                    Text(
                        text = "Leaderboard",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(
                    items = uiState.entries,
                    key = { it.playerId }
                ) { row ->
                    LeaderboardRow(row = row)
                }
            }
        }
    }
}