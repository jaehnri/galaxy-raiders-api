package galaxyraiders.core.score

import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ScoreWriterJSON : ScoreWriter {

  override fun readScoreboard(filepath: String): List<Score> {
    val file = File(filepath)
    if (!file.exists()) {
      println("Scoreboard file does not exist. Creating new scoreboard.")
      return emptyList()
    }

    val fileContent = file.readText()
    return Json.decodeFromString(fileContent)
  }

  override fun writeScoreboard(filepath: String, scoreboard: List<Score>) {
    val json = Json.encodeToString(scoreboard)

    val file = createFileIfNotExists(filepath)
    file.writeText(json)
  }

  override fun readLeaderboard(filepath: String): Leaderboard {
    val file = File(filepath)
    if (!file.exists()) {
      println("Leaderboard file does not exist. Creating new leaderboard.")
      return Leaderboard(Clock.System.now(), mutableListOf())
    }

    val fileContent = file.readText()
    return Json.decodeFromString(fileContent)
  }

  override fun writeLeaderboard(filepath: String, leaderboard: Leaderboard) {
    val json = Json.encodeToString(leaderboard)

    val file = createFileIfNotExists(filepath)
    file.writeText(json)
  }

  private fun createFileIfNotExists(filepath: String): File {
    val file = File(filepath)

    if (!file.exists()) {
      file.createNewFile()
      println("Created file: ${file.absolutePath}" )
    }

    return file
  }
}