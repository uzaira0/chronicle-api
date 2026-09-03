package com.openlattice.chronicle.models

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.openlattice.chronicle.timeusediary.TimeUseDiaryResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime

/**
 * Pins the wire contract of `TimeUseDiaryResponse` — the object the (future) web TUD
 * form must produce. Serialization uses the legacy `ol.*` property names, while
 * deserialization must accept BOTH the `ol.*` names and the friendly aliases
 * (`code`/`question`/`response`/`startDateTime`/`endDateTime`). A regression here would
 * silently break TUD submissions even though the Kotlin field names look unchanged.
 */
class TimeUseDiaryResponseTest {

    private val mapper: ObjectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .registerModule(Jdk8Module())
        .registerModule(KotlinModule.Builder().build())
        .apply {
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        }

    private fun sample() = TimeUseDiaryResponse(
        code = "primaryActivity",
        question = "Primary activity",
        response = setOf("Napping/sleeping"),
        startDateTime = OffsetDateTime.parse("2026-06-01T08:00:00Z"),
        endDateTime = OffsetDateTime.parse("2026-06-01T09:00:00Z"),
    )

    @Test fun serializesWithLegacyOlPropertyNames() {
        val json = mapper.writeValueAsString(sample())
        assertTrue("expected ol.code in $json", json.contains("\"ol.code\""))
        assertTrue("expected ol.title in $json", json.contains("\"ol.title\""))
        assertTrue("expected ol.values in $json", json.contains("\"ol.values\""))
        assertTrue("expected ol.datetimestart in $json", json.contains("\"ol.datetimestart\""))
        assertTrue("expected ol.datetimeend in $json", json.contains("\"ol.datetimeend\""))
    }

    @Test fun roundTripThroughOlNamesIsLossless() {
        val original = sample()
        val restored = mapper.readValue(mapper.writeValueAsString(original), TimeUseDiaryResponse::class.java)
        assertEquals(original, restored)
    }

    @Test fun deserializesFromFriendlyAliases() {
        val aliased = """
            {
              "code": "dayOfWeek",
              "question": "Day of week",
              "response": ["Monday"],
              "startDateTime": null,
              "endDateTime": null
            }
        """.trimIndent()
        val restored = mapper.readValue(aliased, TimeUseDiaryResponse::class.java)
        assertEquals("dayOfWeek", restored.code)
        assertEquals("Day of week", restored.question)
        assertEquals(setOf("Monday"), restored.response)
        assertNull(restored.startDateTime)
        assertNull(restored.endDateTime)
    }

    @Test fun allowsNullStartAndEndTimes() {
        val response = TimeUseDiaryResponse(
            code = "activityDay",
            question = "activityDay",
            response = setOf("yesterday"),
            startDateTime = null,
            endDateTime = null,
        )
        val restored = mapper.readValue(mapper.writeValueAsString(response), TimeUseDiaryResponse::class.java)
        assertEquals(response, restored)
    }
}
