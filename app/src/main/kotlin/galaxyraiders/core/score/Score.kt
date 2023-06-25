package galaxyraiders.core.score

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

object UUIDSerializer : KSerializer<UUID> {
  override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): UUID {
    return UUID.fromString(decoder.decodeString())
  }

  override fun serialize(encoder: Encoder, value: UUID) {
    encoder.encodeString(value.toString())
  }
}

@Serializable
data class Score(
  @Serializable(with = UUIDSerializer::class) val id: UUID,
  var points: Double,
  var destroyedAsteroids: Int,
  @Serializable(with = InstantIso8601Serializer::class) val startTime: Instant,
  @Serializable(with = InstantIso8601Serializer::class) var endTime: Instant?
)
