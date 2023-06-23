package galaxyraiders.core.score

import galaxyraiders.Config
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class Leaderboard(
  @Serializable(with = InstantIso8601Serializer::class) var lastUpdated: Instant,
  var scores: MutableList<Score>
) {
  object LeaderboardConfig {
    private val config = Config(prefix = "GR__SCORE__")
    val LEADERBOARD_SIZE = config.get<Int>("LEADERBOARD_SIZE")
  }

  fun update(newScore: Score) {
    scores.add(newScore)
    scores = scores.sortedByDescending { it.points }.take(LeaderboardConfig.LEADERBOARD_SIZE).toMutableList()
    this.lastUpdated = Clock.System.now()
  }
}