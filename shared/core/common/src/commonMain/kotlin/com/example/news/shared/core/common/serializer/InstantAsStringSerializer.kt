package com.example.news.shared.core.common.serializer

import kotlinx.datetime.Instant
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.alternativeParsing
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object InstantAsStringSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        val format = DateTimeComponents.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char('T')
            hour()
            char(':')
            minute()
            char(':')
            second()
            char('+')
            secondFraction(4)
            alternativeParsing({}) {
                char(':')
                secondFraction(2)
            }
        }
        return Instant.parse(decoder.decodeString(), format)
    }
}
