package com.anilkumar.realtimeleaderboard.core

import com.anilkumar.realtimeleaderboard.leaderboard.coordinator.LeaderboardCoordinator
import com.anilkumar.realtimeleaderboard.leaderboard.domain.RankingEngine
import com.anilkumar.realtimeleaderboard.leaderboard.repository.InMemoryLeaderboardRepository
import com.anilkumar.realtimeleaderboard.scoreengine.engine.RandomScoreGenerator
import com.anilkumar.realtimeleaderboard.scoreengine.model.Player
import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreGenerationConfig

class AppContainer {

    private val players = listOf(
        Player("1", "Deepender", 3200),
        Player("2", "Predekin_Singh", 3100),
        Player("3", "Himanshu", 3000),
        Player("4", "Manya Aggarwal", 2900),
        Player("5", "Vishal", 1450),
        Player("6", "Shreyas", 1200),
        Player("7", "Anwesha", 1000),
        Player("8", "Premjit", 900),
        Player("9", "Harshit", 800),
        Player("10", "Ayush Rawat", 700),
        Player("11", "Arshi", 600),
        Player("12", "Iqbal", 500),
        Player("13", "Shubham", 400),
        Player("14", "Aditya Patil", 300)
    )

    private val scoreGenerator = RandomScoreGenerator(
        config = ScoreGenerationConfig(
            players = players,
            seed = 12345L,
            minDelayMillis = 800L,
            maxDelayMillis = 1800L,
            minScoreDelta = 10,
            maxScoreDelta = 150
        )
    )

    private val repository = InMemoryLeaderboardRepository()
    private val rankingEngine = RankingEngine()

    val leaderboardCoordinator = LeaderboardCoordinator(
        players = players,
        scoreEventSource = scoreGenerator,
        repository = repository,
        rankingEngine = rankingEngine
    )
}