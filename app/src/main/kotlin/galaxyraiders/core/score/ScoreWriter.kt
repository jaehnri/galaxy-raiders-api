package galaxyraiders.core.score

interface ScoreWriter {
  fun readScoreboard(filepath: String): List<Score>
  fun writeScoreboard(filepath: String, scoreboard: List<Score>)

  fun readLeaderboard(filepath: String): Leaderboard
  fun writeLeaderboard(filepath: String, leaderboard: Leaderboard)
}