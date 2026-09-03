package com.openlattice.chronicle.models

import com.openlattice.chronicle.survey.AppUsage
import com.openlattice.chronicle.survey.Question
import com.openlattice.chronicle.survey.Questionnaire
import com.openlattice.chronicle.survey.QuestionnaireResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.Optional
import java.util.UUID

/**
 * Pins the construction-time invariants of the participant-facing survey models.
 * These `init {}` checks are the contract the web Survey/Questionnaire forms submit
 * against, so a regression here would silently let malformed submissions through to
 * the backend. The jakarta `@field:` constraints are enforced separately by Spring's
 * `@Valid`; here we exercise only the invariants the data classes enforce themselves.
 */
class SurveyModelsTest {

    private fun questionnaire(
        title: String = "Daily check-in",
        questions: List<Question> = listOf(Question("How are you?", setOf("Good", "Bad"))),
        recurrenceRule: String? = null,
    ) = Questionnaire(
        id = UUID.randomUUID(),
        title = title,
        dateCreated = OffsetDateTime.now(),
        description = "",
        active = true,
        questions = questions,
        recurrenceRule = recurrenceRule,
    )

    // ===== Questionnaire invariants =====
    @Test fun validQuestionnaireConstructs() {
        val q = questionnaire()
        assertEquals("Daily check-in", q.title)
        assertEquals(1, q.questions.size)
    }

    @Test fun questionnaireRejectsEmptyQuestions() {
        assertThrows(IllegalStateException::class.java) { questionnaire(questions = emptyList()) }
    }

    @Test fun questionnaireRejectsBlankTitle() {
        assertThrows(IllegalStateException::class.java) { questionnaire(title = "   ") }
    }

    @Test fun questionnaireRejectsBlankQuestionTitle() {
        assertThrows(IllegalStateException::class.java) {
            questionnaire(questions = listOf(Question("  ", setOf("a"))))
        }
    }

    @Test fun questionnaireRejectsDuplicateQuestionTitles() {
        assertThrows(IllegalStateException::class.java) {
            questionnaire(
                questions = listOf(
                    Question("Same", setOf("a")),
                    Question("Same", setOf("b")),
                ),
            )
        }
    }

    @Test fun questionnaireAllowsOpenEndedQuestionWithNoChoices() {
        val q = questionnaire(questions = listOf(Question("Describe your day")))
        assertTrue(q.questions.first().choices.isEmpty())
    }

    // ===== QuestionnaireResponse invariants =====
    @Test fun responseRejectsBlankQuestionTitle() {
        assertThrows(IllegalStateException::class.java) { QuestionnaireResponse("   ", setOf("yes")) }
    }

    @Test fun responseRetainsValueSet() {
        val response = QuestionnaireResponse("How are you?", setOf("Good"))
        assertEquals(setOf("Good"), response.value)
    }

    @Test fun responseAllowsEmptyValueSet() {
        // value is @NotNull but an empty set is permitted at the model level (a skipped answer).
        val response = QuestionnaireResponse("How are you?", emptySet())
        assertTrue(response.value.isEmpty())
    }

    // ===== AppUsage label-defaulting =====
    @Test fun appUsageBlankLabelFallsBackToPackageName() {
        val usage = AppUsage(
            appPackageName = "com.example.app",
            appLabel = "   ",
            timestamp = OffsetDateTime.now(),
            eventType = 1,
            users = listOf("Me"),
            timezone = "America/Chicago",
            uploadedAt = Optional.empty(),
        )
        assertEquals("com.example.app", usage.appLabel)
    }

    @Test fun appUsagePreservesNonBlankLabel() {
        val usage = AppUsage(
            appPackageName = "com.example.app",
            appLabel = "Example",
            timestamp = OffsetDateTime.now(),
            eventType = 1,
            users = emptyList(),
            timezone = "UTC",
            uploadedAt = Optional.empty(),
        )
        assertEquals("Example", usage.appLabel)
    }

    @Test fun appUsageDefaultsUsersToEmpty() {
        val usage = AppUsage(
            appPackageName = "com.example.app",
            appLabel = "Example",
            timestamp = OffsetDateTime.now(),
            eventType = 1,
            timezone = "UTC",
            uploadedAt = Optional.empty(),
        )
        assertTrue(usage.users.isEmpty())
    }
}
