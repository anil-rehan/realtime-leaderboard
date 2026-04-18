package com.anilkumar.realtimeleaderboard.ui.leaderboard.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.anilkumar.realtimeleaderboard.leaderboard.model.RankChangeDirection
import com.anilkumar.realtimeleaderboard.ui.leaderboard.model.LeaderboardRowUiModel


@Composable
fun LazyItemScope.LeaderboardRow(
    row: LeaderboardRowUiModel,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (row.isRecentlyUpdated) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "rowBackground"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${row.rank}. ${row.username}")

        val suffix = when (row.rankChangeDirection) {
            RankChangeDirection.UP -> " ↑"
            RankChangeDirection.DOWN -> " ↓"
            RankChangeDirection.NONE -> ""
        }

        Text(text = "${row.score}$suffix")
    }
}