package galaxyraiders.core.score

import galaxyraiders.core.game.Asteroid
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleScoreCalculatorTest {
  private val simpleScoreCalculator = SimpleScoreCalculator()

  @Test
  fun `it should use the asteroid mass and radius`() {
    var asteroid = Asteroid(
      initialPosition = Point2D(1.0, 1.0),
      initialVelocity = Vector2D(1.0, 0.0),
      radius = 1.0,
      mass = 1.0
    )
    assertEquals(1.0, simpleScoreCalculator.calculate(asteroid))

    asteroid = Asteroid(
      initialPosition = Point2D(1.0, 1.0),
      initialVelocity = Vector2D(1.0, 0.0),
      radius = 2.0,
      mass = 2.0
    )
    assertEquals(4.0, simpleScoreCalculator.calculate(asteroid))

    asteroid = Asteroid(
      initialPosition = Point2D(1.0, 1.0),
      initialVelocity = Vector2D(1.0, 0.0),
      radius = 0.0,
      mass = 2.0
    )
    assertEquals(0.0, simpleScoreCalculator.calculate(asteroid))
  }
}