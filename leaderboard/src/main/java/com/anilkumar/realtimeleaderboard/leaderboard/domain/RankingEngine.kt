package com.anilkumar.realtimeleaderboard.leaderboard.domain

import com.anilkumar.realtimeleaderboard.leaderboard.model.LeaderboardSnapshot
import com.anilkumar.realtimeleaderboard.leaderboard.model.PlayerScoreState
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankChangeDirection
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankedPlayer

class RankingEngine {

    fun buildSnapshot(
        currentScores: List<PlayerScoreState>,
        previousSnapshot: LeaderboardSnapshot?,
        updatedPlayerId: String?
    ): LeaderboardSnapshot {
        val previousRanks = previousSnapshot?.players
            ?.associate { it.playerId to it.rank }
            .orEmpty()

        val sortedPlayers = currentScores.sortedWith(
            compareByDescending<PlayerScoreState> { it.score }
                .thenBy { it.username }
        )

        val rankedPlayers = mutableListOf<RankedPlayer>()

        var lastScore: Int? = null
        var currentRank = 0

        sortedPlayers.forEachIndexed { index, player ->
            if (lastScore == null || player.score != lastScore) {
                currentRank = index + 1
                lastScore = player.score
            }

            val previousRank = previousRanks[player.playerId]
            val direction = when {
                previousRank == null -> RankChangeDirection.NONE
                currentRank < previousRank -> RankChangeDirection.UP
                currentRank > previousRank -> RankChangeDirection.DOWN
                else -> RankChangeDirection.NONE
            }

            rankedPlayers.add(
                RankedPlayer(
                    playerId = player.playerId,
                    username = player.username,
                    score = player.score,
                    rank = currentRank,
                    previousRank = previousRank,
                    rankChangeDirection = direction,
                    isRecentlyUpdated = player.playerId == updatedPlayerId
                )
            )
        }

        return LeaderboardSnapshot(
            players = rankedPlayers,
            lastUpdatedPlayerId = updatedPlayerId
        )
    }


}

