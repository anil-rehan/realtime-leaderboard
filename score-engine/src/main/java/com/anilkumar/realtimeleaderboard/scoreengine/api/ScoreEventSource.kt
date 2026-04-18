package com.anilkumar.realtimeleaderboard.scoreengine.api

import com.anilkumar.realtimeleaderboard.scoreengine.model.ScoreUpdate
import kotlinx.coroutines.flow.Flow

interface ScoreEventSource {

    fun scoreUpdates(): Flow<ScoreUpdate>

}