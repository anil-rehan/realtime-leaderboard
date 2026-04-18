package com.anilkumar.realtimeleaderboard.scoreengine.engine

import com.anilkumar.realtimeleaderboard.scoreengine.api.ScoreEventSource
import com.anilkumar.realtimeleaderboard.scoreengine.model.Player
import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreGenerationConfig
import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class RandomScoreGenerator(
    private val config: ScoreGenerationConfig
) : ScoreEventSource {

    override fun scoreUpdates(): Flow<ScoreUpdate> = flow {
        require(config.players.isNotEmpty()) { "Players list cannot be empty." }
        require(config.minDelayMillis <= config.maxDelayMillis) { "Invalid delay range." }
        require(config.minScoreDelta <= config.maxScoreDelta) { "Invalid score delta range." }

        val random = Random(config.seed)
        val currentScores = config.players.associate { player ->
            player.id to player.initialScore
        }.toMutableMap()

        while (true) {
            val delayMillis = random.nextLong(
                from = config.minDelayMillis,
                until = config.maxDelayMillis + 1
            )
            delay(delayMillis)

            val selectedPlayer: Player = config.players[random.nextInt(config.players.size)]
            val delta = random.nextInt(
                from = config.minScoreDelta,
                until = config.maxScoreDelta + 1
            )

            val updatedScore = (currentScores[selectedPlayer.id] ?: selectedPlayer.initialScore) + delta
            currentScores[selectedPlayer.id] = updatedScore

            emit(
                ScoreUpdate(
                    playerId = selectedPlayer.id,
                    newScore = updatedScore,
                    scoreDelta = delta,
                    emittedAtMillis = System.currentTimeMillis()
                )
            )
        }
    }
}