@file:Suppress("MatchingDeclarationName")
package galaxyraiders

import galaxyraiders.adapters.BasicRandomGenerator
import galaxyraiders.adapters.tui.TextUserInterface
import galaxyraiders.adapters.web.WebUserInterface
import galaxyraiders.core.game.GameEngine
import galaxyraiders.core.score.Score
import galaxyraiders.core.score.ScoreManager
import galaxyraiders.core.score.ScoreWriterJSON
import galaxyraiders.core.score.SimpleScoreCalculator
import kotlinx.datetime.Clock
import java.util.UUID
import kotlin.concurrent.thread
import kotlin.random.Random

object AppConfig {
  val config = Config("GR__APP__")

  val randomSeed = config.get<Int>("RANDOM_SEED")
  val scoreboardFile = config.get<String>("SCOREBOARD_FILE")
  val leaderboardFile = config.get<String>("LEADERBOARD_FILE")
  val operationMode = config.get<OperationMode>("OPERATION_MODE")
}

fun main() {
  val generator = BasicRandomGenerator(
    rng = Random(seed = AppConfig.randomSeed)
  )

  val ui = when (AppConfig.operationMode) {
    OperationMode.Text -> TextUserInterface()
    OperationMode.Web -> WebUserInterface()
  }

  val scoreManager = ScoreManager(
    score = Score(
      id = UUID.randomUUID(),
      startTime = Clock.System.now(),
      endTime = null,
      points = 0.0,
      destroyedAsteroids = 0
    ),
    scoreboardFile = AppConfig.scoreboardFile,
    leaderboardFile = AppConfig.leaderboardFile,
    scoreWriter = ScoreWriterJSON(),
    scoreCalculator = SimpleScoreCalculator()
  )

  val (controller, visualizer) = ui.build()

  val gameEngine = GameEngine(
    generator, controller, visualizer, scoreManager
  )
  thread { gameEngine.execute() }

  ui.start()
}
