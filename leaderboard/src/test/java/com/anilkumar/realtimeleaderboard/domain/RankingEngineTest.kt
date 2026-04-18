package com.anilkumar.realtimeleaderboard.domain

import com.anilkumar.realtimeleaderboard.leaderboard.domain.RankingEngine
import com.anilkumar.realtimeleaderboard.leaderboard.model.LeaderboardSnapshot
import com.anilkumar.realtimeleaderboard.leaderboard.model.PlayerScoreState
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankChangeDirection
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankedPlayer
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RankingEngineTest {

    private val rankingEngine = RankingEngine()

    @Test
    fun buildSnapshot_sortsPlayersByScoreDescending() {
        val players = listOf(
            PlayerScoreState(playerId = "1", username = "A", score = 100),
            PlayerScoreState(playerId = "2", username = "B", score = 300),
            PlayerScoreState(playerId = "3", username = "C", score = 200)
        )

        val result = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        assertEquals("2", result.players[0].playerId)
        assertEquals("3", result.players[1].playerId)
        assertEquals("1", result.players[2].playerId)

        assertEquals(1, result.players[0].rank)
        assertEquals(2, result.players[1].rank)
        assertEquals(3, result.players[2].rank)
    }

    @Test
    fun buildSnapshot_assignsSameRankForSameScore() {
        val players = listOf(
            PlayerScoreState(playerId = "1", username = "A", score = 300),
            PlayerScoreState(playerId = "2", username = "B", score = 300),
            PlayerScoreState(playerId = "3", username = "C", score = 100)
        )

        val result = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        assertEquals(1, result.players[0].rank)
        assertEquals(1, result.players[1].rank)
        assertEquals(3, result.players[2].rank)
    }

    @Test
    fun buildSnapshot_skipsRankAfterTie() {
        val players = listOf(
            PlayerScoreState(playerId = "1", username = "A", score = 500),
            PlayerScoreState(playerId = "2", username = "B", score = 500),
            PlayerScoreState(playerId = "3", username = "C", score = 400),
            PlayerScoreState(playerId = "4", username = "D", score = 300)
        )

        val result = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        assertEquals(1, result.players[0].rank)
        assertEquals(1, result.players[1].rank)
        assertEquals(3, result.players[2].rank)
        assertEquals(4, result.players[3].rank)
    }

    @Test
    fun buildSnapshot_detectsOvertakeAfterScoreUpdate() {
        val previousSnapshot = LeaderboardSnapshot(
            players = listOf(
                RankedPlayer(
                    playerId = "1",
                    username = "A",
                    score = 100,
                    rank = 1,
                    previousRank = null,
                    rankChangeDirection = RankChangeDirection.NONE,
                    isRecentlyUpdated = false
                ),
                RankedPlayer(
                    playerId = "2",
                    username = "B",
                    score = 90,
                    rank = 2,
                    previousRank = null,
                    rankChangeDirection = RankChangeDirection.NONE,
                    isRecentlyUpdated = false
                )
            ),
            lastUpdatedPlayerId = null
        )

        val currentScores = listOf(
            PlayerScoreState(playerId = "1", username = "A", score = 100),
            PlayerScoreState(playerId = "2", username = "B", score = 120)
        )

        val result = rankingEngine.buildSnapshot(
            currentScores = currentScores,
            previousSnapshot = previousSnapshot,
            updatedPlayerId = "2"
        )

        assertEquals("2", result.players[0].playerId)
        assertEquals(1, result.players[0].rank)
        assertEquals(2, result.players[0].previousRank)
        assertEquals(RankChangeDirection.UP, result.players[0].rankChangeDirection)

        assertEquals("1", result.players[1].playerId)
        assertEquals(2, result.players[1].rank)
        assertEquals(1, result.players[1].previousRank)
        assertEquals(RankChangeDirection.DOWN, result.players[1].rankChangeDirection)
    }

    @Test
    fun buildSnapshot_keepsStableOrderForEqualScoresByUsername() {
        val players = listOf(
            PlayerScoreState(playerId = "2", username = "Bravo", score = 200),
            PlayerScoreState(playerId = "1", username = "Alpha", score = 200),
            PlayerScoreState(playerId = "3", username = "Charlie", score = 200)
        )

        val result = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        assertEquals("1", result.players[0].playerId)
        assertEquals("2", result.players[1].playerId)
        assertEquals("3", result.players[2].playerId)

        assertEquals(1, result.players[0].rank)
        assertEquals(1, result.players[1].rank)
        assertEquals(1, result.players[2].rank)
    }
}