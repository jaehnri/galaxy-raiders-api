package galaxyraiders.core.score

import galaxyraiders.core.game.Asteroid

interface ScoreCalculator {
  fun calculate(asteroid: Asteroid): Double
}
