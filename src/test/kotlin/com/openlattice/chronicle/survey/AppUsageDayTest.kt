package com.openlattice.chronicle.survey

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.validation.Validation
import java.time.LocalDate
import java.time.OffsetDateTime
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import org.junit.Assert.assertEquals
import org.junit.Test

/** The day survey's wire shape, as the participant survey page reads and answers it, and the answer's bounds. */
class AppUsageDayTest {
    private val mapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .registerModule(KotlinModule.Builder().build())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

    @Test
    fun theDayListReadsAndWritesCalendarDates() {
        val json = """{"timeZone":"America/Santiago","today":"2026-10-05","days":[{"date":"2026-10-04","state":"NEW_APPS"}]}"""
        val days = AppUsageSurveyDays(
            timeZone = "America/Santiago",
            today = LocalDate.parse("2026-10-05"),
            days = listOf(AppUsageSurveyDay(LocalDate.parse("2026-10-04"), AppUsageDayState.NEW_APPS)),
        )
        assertEquals(days, mapper.readValue(json, AppUsageSurveyDays::class.java))
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(days)))
    }

    @Test
    fun anUnansweredBlockHasNoUsersAndAnAnsweredOneKeepsThem() {
        val json = """{"date":"2026-10-04","timeZone":"UTC","state":"UNANSWERED","apps":[{"appPackageName":"org.example",
            |"appLabel":"Example","blocks":[{"start":"2026-10-04T12:00:00Z","end":"2026-10-04T13:00:00Z"},
            |{"start":"2026-10-04T13:00:00Z","end":"2026-10-04T14:00:00Z","users":["Child"]}]}]}""".trimMargin()
        val survey = mapper.readValue(json, AppUsageDaySurvey::class.java)
        val noon = OffsetDateTime.parse("2026-10-04T12:00:00Z")
        assertEquals(
            AppUsageDaySurvey(
                date = LocalDate.parse("2026-10-04"),
                timeZone = "UTC",
                state = AppUsageDayState.UNANSWERED,
                apps = listOf(AppUsageDayApp("org.example", "Example", listOf(
                    AppUsageBlock(noon, noon.plusHours(1)),
                    AppUsageBlock(noon.plusHours(1), noon.plusHours(2), listOf("Child")),
                ))),
            ),
            survey,
        )
        assertEquals(survey, mapper.readValue(mapper.writeValueAsString(survey), AppUsageDaySurvey::class.java))
    }

    @Test
    fun anAnswerReadsAndWritesTheSurveyPagesShape() {
        val json = """{"appPackageName":"org.example","blockStart":"2026-10-04T12:00:00Z","users":["Child"]}"""
        val answer = AppUsageAnswer("org.example", OffsetDateTime.parse("2026-10-04T12:00:00Z"), listOf("Child"))
        assertEquals(answer, mapper.readValue(json, AppUsageAnswer::class.java))
        assertEquals(mapper.readTree(json), mapper.readTree(mapper.writeValueAsString(answer)))
    }

    @Test
    fun anAnswerOutsideItsBoundsIsRefused() {
        val start = OffsetDateTime.parse("2026-10-04T12:00:00Z")
        val validator = Validation.byDefaultProvider().configure()
            .messageInterpolator(ParameterMessageInterpolator()).buildValidatorFactory().validator
        val violations = { answer: AppUsageAnswer -> validator.validate(answer).map { it.message }.toSet() }
        assertEquals(emptySet<String>(), violations(AppUsageAnswer("org.example", start, listOf("Child"))))
        assertEquals(setOf("App package name is required"), violations(AppUsageAnswer(" ", start, listOf())))
        assertEquals(setOf("App package name exceeds maximum length"),
            violations(AppUsageAnswer("a".repeat(501), start, listOf())))
        assertEquals(setOf("Users list exceeds maximum count"),
            violations(AppUsageAnswer("org.example", start, List(101) { "Child" })))
    }
}
