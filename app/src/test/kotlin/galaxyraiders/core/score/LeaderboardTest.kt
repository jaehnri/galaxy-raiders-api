package galaxyraiders.core.score

import kotlinx.datetime.Instant
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Given a leaderboard")
class LeaderboardTest {
  private val leaderboard = Leaderboard(
    lastUpdated = Instant.DISTANT_PAST,
    scores = mutableListOf(
      Score(
        id = UUID.randomUUID(),
        points = 0.0,
        destroyedAsteroids = 0,
        startTime = Instant.DISTANT_PAST,
        endTime = Instant.DISTANT_FUTURE
      ),
      Score(
        id = UUID.randomUUID(),
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
      id = UUID.randomUUID(),
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
      id = UUID.randomUUID(),
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
      id = UUID.randomUUID(),
      points = 10.0,
      destroyedAsteroids = 3,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    val middleScore = Score(
      id = UUID.randomUUID(),
      points = 5.0,
      destroyedAsteroids = 2,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    val lowestScore = Score(
      id = UUID.randomUUID(),
      points = 1.0,
      destroyedAsteroids = 1,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )

    leaderboard.update(middleScore)
    leaderboard.update(highestScore)
    leaderboard.update(lowestScore)
    assertEquals(mutableListOf(highestScore, middleScore, lowestScore), leaderboard.scores)
  }

  @Test
  fun `if score already present, should only update its points`() {
    val newScore = Score(
      id = UUID.randomUUID(),
      points = 10.0,
      destroyedAsteroids = 1,
      startTime = Instant.DISTANT_PAST,
      endTime = Instant.DISTANT_FUTURE
    )
    leaderboard.update(newScore)
    assertEquals(10.0, leaderboard.scores.find { it.id == newScore.id }?.points)

    newScore.points += 10.0
    leaderboard.update(newScore)
    assertEquals(20.0, leaderboard.scores.find { it.id == newScore.id }?.points)

    newScore.points += 10.0
    leaderboard.update(newScore)
    assertEquals(30.0, leaderboard.scores.find { it.id == newScore.id }?.points)
  }
}
