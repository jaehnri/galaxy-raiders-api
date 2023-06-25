package galaxyraiders.core.score

import kotlinx.datetime.Instant
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Given a leaderboard")
class LeaderboardTest {
  private val leaderboard = Leaderboard(
    lastUpdated = Instant.DISTANT_PAST,
    scores = mutableListOf(
      Score(
        points = 0.0,
        destroyedAsteroids = 0,
        startTime = Instant.DISTANT_PAST,
        endTime = Instant.DISTANT_FUTURE
      ),
      Score(
        points = 0.0,
        destroyedAsteroids = 0,
        startTime = Instant.DISTANT_PAST,
        endTime = Instant.DISTANT_FUTURE
      )
    )
  )

  @Test
  fun `it should contain at most 3 scores`() {
    val newScore = Score(
      points = 10.0,
      destroyedAsteroids = 1,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    leaderboard.update(newScore)
    leaderboard.update(newScore)
    leaderboard.update(newScore)
    leaderboard.update(newScore)
    assertEquals(3, leaderboard.scores.size)
  }

  @Test
  fun `it should update the lastUpdated variable`() {
    val newScore = Score(
      points = 10.0,
      destroyedAsteroids = 1,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )

    leaderboard.update(newScore)
    assertTrue { leaderboard.lastUpdated > Instant.DISTANT_PAST }
  }

  @Test
  fun `it should update maintaining the descending order`() {
    val highestScore = Score(
      points = 10.0,
      destroyedAsteroids = 3,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    val middleScore = Score(
      points = 5.0,
      destroyedAsteroids = 2,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    val lowestScore = Score(
      points = 1.0,
      destroyedAsteroids = 1,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )

    leaderboard.update(middleScore)
    leaderboard.update(highestScore)
    leaderboard.update(lowestScore)
    assertEquals(mutableListOf(highestScore, middleScore, lowestScore),  leaderboard.scores)
  }
}