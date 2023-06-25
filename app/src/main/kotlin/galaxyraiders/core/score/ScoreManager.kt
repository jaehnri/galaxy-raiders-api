package galaxyraiders.core.score

import galaxyraiders.core.game.Asteroid
import kotlinx.datetime.Clock

class ScoreManager(
  var score: Score,
  private val scoreboardFile: String,
  private val leaderboardFile: String,
  private val scoreWriter: ScoreWriter,
  private val scoreCalculator: ScoreCalculator,
) {
  fun addDestroyedAsteroid(asteroid: Asteroid) {
    score.points += scoreCalculator.calculate(asteroid)
    score.destroyedAsteroids++
  }

  private fun updateLeaderboard() {
    val leaderboard = scoreWriter.readLeaderboard(leaderboardFile)
    leaderboard.update(score)

    scoreWriter.writeLeaderboard(leaderboardFile, leaderboard)
  }

  private fun updateScoreboard() {
    val scoreboard = scoreWriter.readScoreboard(scoreboardFile).toMutableList()
    scoreboard.add(score)

    scoreWriter.writeScoreboard(scoreboardFile, scoreboard)
  }

  fun updateRankings() {
    score.endTime = Clock.System.now()

    updateScoreboard()
    updateLeaderboard()
  }
}
