package galaxyraiders.core.score

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class Score (
  var points: Double,
  var destroyedAsteroids: Int,
  @Serializable(with = InstantIso8601Serializer::class) val startTime: Instant,
  @Serializable(with = InstantIso8601Serializer::class) var endTime: Instant?
  )
