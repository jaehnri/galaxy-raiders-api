package galaxyraiders.core.game

import galaxyraiders.Config
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import galaxyraiders.ports.RandomGenerator

object SpaceFieldConfig {
  private val config = Config(prefix = "GR__CORE__GAME__SPACE_FIELD__")

  val missileRadius = config.get<Double>("MISSILE_RADIUS")
  val missileMass = config.get<Double>("MISSILE_MASS")
  val missileDistanceFromShip = config.get<Double>("MISSILE_DISTANCE_FROM_SHIP")

  val asteroidMaxYaw = config.get<Double>("ASTEROID_MAX_YAW")
  val asteroidMinSpeed = config.get<Double>("ASTEROID_MIN_SPEED")
  val asteroidMaxSpeed = config.get<Double>("ASTEROID_MAX_SPEED")

  val asteroidMinRadius = config.get<Int>("ASTEROID_MIN_RADIUS")
  val asteroidMaxRadius = config.get<Int>("ASTEROID_MAX_RADIUS")
  val asteroidRadiusMultiplier = config.get<Double>("ASTEROID_RADIUS_MULTIPLIER")

  val asteroidMinMass = config.get<Int>("ASTEROID_MIN_MASS")
  val asteroidMaxMass = config.get<Int>("ASTEROID_MAX_MASS")
  val asteroidMassMultiplier = config.get<Double>("ASTEROID_MASS_MULTIPLIER")

  val explosionMass = config.get<Double>("EXPLOSION_MASS")
  val explosionRadius = config.get<Double>("EXPLOSION_RADIUS")
  val explosionLife = config.get<Int>("EXPLOSION_LIFE")
}

@Suppress("TooManyFunctions")
data class SpaceField(val width: Int, val height: Int, val generator: RandomGenerator) {
  val boundaryX = 0.0..width.toDouble()
  val boundaryY = 0.0..height.toDouble()

  val ship = initializeShip()

  var missiles: List<Missile> = emptyList()
    private set

  var asteroids: List<Asteroid> = emptyList()
    private set

  var explosions: List<Explosion> = emptyList()
    private set

  val spaceObjects: List<SpaceObject>
    get() = listOf(this.ship) + this.missiles + this.asteroids + this.explosions

  fun moveShip() {
    this.ship.move(boundaryX, boundaryY)
  }

  fun moveMissiles() {
    this.missiles.forEach { it.move() }
  }

  fun moveAsteroids() {
    this.asteroids.forEach { it.move() }
  }

  fun generateMissile(customPos: Point2D = Point2D(-1.0, -1.0)) {
    this.missiles += this.createMissile(customPos)
  }

  fun generateAsteroid() {
    this.asteroids += this.createAsteroidWithRandomProperties()
  }

  fun generateExplosion(position: Point2D) {
    this.explosions += this.createExplosion(position)
  }

  fun trimMissiles() {
    this.missiles = this.missiles.filter { it.inBoundaries(this.boundaryX, this.boundaryY) }
  }

  fun trimAsteroids() {
    this.asteroids = this.asteroids.filter { it.inBoundaries(this.boundaryX, this.boundaryY) }
  }

  fun trimExplosions() {
    this.explosions =
      this.explosions.filter {
        it.inBoundaries(this.boundaryX, this.boundaryY) &&
          (it.life < SpaceFieldConfig.explosionLife)
      }
  }

  fun incrementExplosionsLife() {
    this.explosions.forEach { it -> it.life = it.life + 1 }
  }

  private fun initializeShip(): SpaceShip {
    return SpaceShip(
      initialPosition = standardShipPosition(),
      initialVelocity = standardShipVelocity(),
      radius = 1.0,
      mass = 10.0,
    )
  }

  private fun standardShipPosition(): Point2D {
    return Point2D(x = this.width / 2.0, y = 1.0)
  }

  private fun standardShipVelocity(): Vector2D {
    return Vector2D(dx = 0.0, dy = 0.0)
  }

  private fun createMissile(customPos: Point2D): Missile {
    return Missile(
      initialPosition = defineMissilePosition(SpaceFieldConfig.missileRadius, customPos),
      initialVelocity = defineMissileVelocity(),
      radius = SpaceFieldConfig.missileRadius,
      mass = SpaceFieldConfig.missileMass,
    )
  }

  private fun defineMissilePosition(missileRadius: Double, customPos: Point2D): Point2D {

    if (customPos.x == -1.0 && customPos.y == -1.0) {
      return ship.center +
        Vector2D(
          dx = 0.0,
          dy = ship.radius + missileRadius + SpaceFieldConfig.missileDistanceFromShip
        )
    } else {
      return customPos
    }
  }

  private fun defineMissileVelocity(): Vector2D {
    return Vector2D(dx = 0.0, dy = 1.0)
  }

  private fun createAsteroidWithRandomProperties(): Asteroid {
    return Asteroid(
      initialPosition = generateRandomAsteroidPosition(),
      initialVelocity = generateRandomAsteroidVelocity(),
      radius = generateRandomAsteroidRadius(),
      mass = generateRandomAsteroidMass(),
    )
  }

  private fun generateRandomAsteroidPosition(): Point2D {
    return Point2D(
      x = this.generator.generateIntegerInRange(0, width).toDouble(),
      y = this.height.toDouble(),
    )
  }

  private fun generateRandomAsteroidVelocity(): Vector2D {
    val asteroidYaw =
      this.generator.generateDoubleInInterval(
        min = -SpaceFieldConfig.asteroidMaxYaw,
        max = SpaceFieldConfig.asteroidMaxYaw
      )

    val asteroidSpeed =
      -1 *
        this.generator.generateDoubleInInterval(
          min = SpaceFieldConfig.asteroidMinSpeed,
          max = SpaceFieldConfig.asteroidMaxSpeed,
        )

    return Vector2D(dx = asteroidYaw, dy = asteroidSpeed)
  }

  private fun generateRandomAsteroidRadius(): Double {
    val scaledRadius =
      this.generator.generateIntegerInRange(
        min = SpaceFieldConfig.asteroidMinRadius,
        max = SpaceFieldConfig.asteroidMaxRadius,
      )

    return scaledRadius * SpaceFieldConfig.asteroidRadiusMultiplier
  }

  private fun generateRandomAsteroidMass(): Double {
    val scaledMass =
      this.generator.generateIntegerInRange(
        min = SpaceFieldConfig.asteroidMinMass,
        max = SpaceFieldConfig.asteroidMaxMass,
      )

    return scaledMass * SpaceFieldConfig.asteroidMassMultiplier
  }

  private fun createExplosion(position: Point2D): Explosion {
    return Explosion(
      initialPosition = position,
      initialVelocity = Vector2D(0.0, 0.0),
      radius = SpaceFieldConfig.explosionRadius,
      mass = SpaceFieldConfig.explosionMass,
      life = 0
    )
  }
}
