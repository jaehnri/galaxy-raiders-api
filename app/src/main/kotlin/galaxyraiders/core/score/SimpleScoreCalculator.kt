package galaxyraiders.core.score

import galaxyraiders.core.game.Asteroid

class SimpleScoreCalculator : ScoreCalculator {
  override fun calculate(asteroid: Asteroid): Double {
    return asteroid.mass * asteroid.radius
  }
}
