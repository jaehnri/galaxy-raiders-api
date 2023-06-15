@file:Suppress("UNUSED_PARAMETER") // <- REMOVE

package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.math.atan2
import kotlin.math.sqrt

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = sqrt(dx * dx + dy * dy)

  val radiant: Double
    get() = atan2(dy, dx)

  val degree: Double
    get() = Math.toDegrees(radiant)

  val unit: Vector2D
    get() = this / this.magnitude

  val normal: Vector2D
    get() = Vector2D(
      dy,
      - dx
    ).unit

  operator fun times(scalar: Double): Vector2D {
    return Vector2D(
      dx * scalar,
      dy * scalar
    )
  }

  operator fun div(scalar: Double): Vector2D {
    return Vector2D(
      dx / scalar,
      dy / scalar
    )
  }

  operator fun times(v: Vector2D): Double {
    return dx * v.dx + dy * v.dy
  }

  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(
      dx + v.dx,
      dy + v.dy
    )
  }

  operator fun plus(p: Point2D): Point2D {
    return Point2D(
      dx + p.x,
      dy + p.y
    )
  }

  operator fun unaryMinus(): Vector2D {
    return Vector2D(
      - dx,
      - dy
    )
  }

  operator fun minus(v: Vector2D): Vector2D {
    return Vector2D(
      dx - v.dx,
      dy - v.dy
    )
  }

  fun scalarProject(target: Vector2D): Double {
    return (this * target) / target.magnitude
  }

  fun vectorProject(target: Vector2D): Vector2D {
    return scalarProject(target) * target.unit
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return v.times(this)
}
