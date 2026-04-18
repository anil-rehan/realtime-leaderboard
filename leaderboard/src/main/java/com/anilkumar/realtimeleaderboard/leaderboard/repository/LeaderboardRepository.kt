package com.anilkumar.realtimeleaderboard.leaderboard.repository

import com.anilkumar.realtimeleaderboard.leaderboard.model.PlayerScoreState
import com.anilkumar.realtimeleaderboard.scoreengine.model.Player
import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreUpdate

interface LeaderboardRepository {

    fun initialize(players: List<Player>)
    fun applyScoreUpdate(scoreUpdate: ScoreUpdate)
    fun getCurrentScores(): List<PlayerScoreState>
}