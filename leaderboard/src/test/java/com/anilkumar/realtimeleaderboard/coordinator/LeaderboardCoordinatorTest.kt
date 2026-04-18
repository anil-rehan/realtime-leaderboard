package com.anilkumar.realtimeleaderboard.coordinator

import com.anilkumar.realtimeleaderboard.leaderboard.domain.RankingEngine
import com.anilkumar.realtimeleaderboard.leaderboard.model.PlayerScoreState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LeaderboardCoordinatorTest {

    private val rankingEngine = RankingEngine()

    @Test
    fun coordinator_emitsInitialSnapshot() = runTest {
        val players = listOf(
            PlayerScoreState("1", "A", 100),
            PlayerScoreState("2", "B", 200)
        )

        val snapshot = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        assertEquals(2, snapshot.players.size)
        assertEquals("2", snapshot.players[0].playerId)
        assertEquals(1, snapshot.players[0].rank)
    }

    @Test
    fun coordinator_detectsRankChange() = runTest {

        val initialPlayers = listOf(
            PlayerScoreState("1", "A", 100),
            PlayerScoreState("2", "B", 200)
        )

        val previous = rankingEngine.buildSnapshot(
            currentScores = initialPlayers,
            previousSnapshot = null,
            updatedPlayerId = null
        )

        val updatedPlayers = listOf(
            PlayerScoreState("1", "A", 300),
            PlayerScoreState("2", "B", 200)
        )

        val snapshot = rankingEngine.buildSnapshot(
            currentScores = updatedPlayers,
            previousSnapshot = previous,
            updatedPlayerId = "1"
        )

        assertEquals("1", snapshot.players[0].playerId)
        assertEquals(1, snapshot.players[0].rank)
        assertEquals(2, snapshot.players[0].previousRank)
    }

    @Test
    fun coordinator_marksUpdatedPlayer() = runTest {

        val players = listOf(
            PlayerScoreState("1", "A", 100),
            PlayerScoreState("2", "B", 200)
        )

        val snapshot = rankingEngine.buildSnapshot(
            currentScores = players,
            previousSnapshot = null,
            updatedPlayerId = "1"
        )

        val updatedPlayer = snapshot.players.find { it.playerId == "1" }

        assertEquals(true, updatedPlayer?.isRecentlyUpdated)
    }
}