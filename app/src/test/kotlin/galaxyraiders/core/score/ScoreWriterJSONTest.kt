package galaxyraiders.core.score

import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ScoreWriterJSONTest {
  private val scoreWriter = ScoreWriterJSON()

  private val emptyLeaderboard = Leaderboard(Instant.DISTANT_PAST, mutableListOf())

  @Test
  fun `read non existent leaderboard should return empty leaderboard`() {
    Score(
      points = 0.0,
      destroyedAsteroids = 0,
      startTime = Instant.DISTANT_PAST,
      endTime = null
    )

    val nonexistentFile = "nonexistentFile"
    val readLeaderboard = scoreWriter.readLeaderboard(nonexistentFile)

    assertEquals(emptyLeaderboard.lastUpdated, readLeaderboard.lastUpdated)
    assertEquals(emptyLeaderboard.scores, readLeaderboard.scores)
  }

  @Test
  fun `read non existent scoreboard should return empty score list`() {
    val nonexistentFile = "nonexistentFile"
    val readScoreboard = scoreWriter.readScoreboard(nonexistentFile)

    assertEquals(mutableListOf(), readScoreboard)
  }
}
