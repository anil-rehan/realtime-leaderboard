package com.anilkumar.realtimeleaderboard.ui.leaderboard.viewmodel

import com.anilkumar.realtimeleaderboard.leaderboard.coordinator.LeaderboardCoordinatorContract
import com.anilkumar.realtimeleaderboard.leaderboard.model.LeaderboardSnapshot
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankChangeDirection
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankedPlayer
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LeaderboardViewModelTest {

    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_mapsSnapshotToUiRows() = runTest {
        val snapshotFlow = MutableStateFlow(
            LeaderboardSnapshot(
                players = listOf(
                    RankedPlayer(
                        playerId = "1",
                        username = "Alice",
                        score = 300,
                        rank = 1,
                        previousRank = null,
                        rankChangeDirection = RankChangeDirection.NONE,
                        isRecentlyUpdated = false
                    ),
                    RankedPlayer(
                        playerId = "2",
                        username = "Bob",
                        score = 200,
                        rank = 2,
                        previousRank = 3,
                        rankChangeDirection = RankChangeDirection.UP,
                        isRecentlyUpdated = true
                    )
                ),
                lastUpdatedPlayerId = "2"
            )
        )

        val coordinator = FakeCoordinator(snapshotFlow)
        val viewModel = LeaderboardViewModel(coordinator)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(2, state.entries.size)

        assertEquals("Alice", state.entries[0].username)
        assertEquals(300, state.entries[0].score)
        assertEquals(1, state.entries[0].rank)

        assertEquals("Bob", state.entries[1].username)
        assertEquals(200, state.entries[1].score)
        assertEquals(RankChangeDirection.UP, state.entries[1].rankChangeDirection)
        assertTrue(state.entries[1].isRecentlyUpdated)
    }

    private class FakeCoordinator(
        private val backingFlow: MutableStateFlow<LeaderboardSnapshot>
    ) : LeaderboardCoordinatorContract {

        override val snapshot: StateFlow<LeaderboardSnapshot>
            get() = backingFlow

        override fun start(scope: CoroutineScope) = Unit
    }
}
