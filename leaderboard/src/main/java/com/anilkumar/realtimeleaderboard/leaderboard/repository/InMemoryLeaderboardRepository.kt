package com.anilkumar.realtimeleaderboard.leaderboard.repository

import com.anilkumar.realtimeleaderboard.leaderboard.model.PlayerScoreState
import com.anilkumar.realtimeleaderboard.scoreengine.model.Player
import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreUpdate

class InMemoryLeaderboardRepository : LeaderboardRepository {

    private val scoresByPlayerId = linkedMapOf<String, PlayerScoreState>()

    override fun initialize(players: List<Player>) {
        scoresByPlayerId.clear()
        players.forEach { player ->
            scoresByPlayerId[player.id] = PlayerScoreState(
                playerId = player.id,
                username = player.username,
                score = player.initialScore
            )
        }
    }

    override fun applyScoreUpdate(scoreUpdate: ScoreUpdate) {
        val existing = scoresByPlayerId[scoreUpdate.playerId] ?: return
        scoresByPlayerId[scoreUpdate.playerId] = existing.copy(
            score = scoreUpdate.newScore
        )
    }

    override fun getCurrentScores(): List<PlayerScoreState> {
        return scoresByPlayerId.values.toList()
    }
}