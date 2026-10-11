package com.openlattice.chronicle.survey

import org.junit.Assert.assertThrows
import org.junit.Test

class QuestionnaireUpdateTest {
    private fun update(vararg titles: String) =
        QuestionnaireUpdate("Survey", null, null, null, titles.map { Question(it) })

    private fun create(vararg titles: String) =
        Questionnaire(null, "Survey", null, questions = titles.map { Question(it) }, recurrenceRule = null)

    @Test
    fun `update rejects an empty question list but permits omitted questions`() {
        assertThrows(IllegalStateException::class.java) { update() }
        QuestionnaireUpdate("Survey", null, null, true, null)
    }

    @Test
    fun `update rejects question titles that create rejects`() {
        assertThrows(IllegalStateException::class.java) { update("Mood", "Mood") }
        assertThrows(IllegalStateException::class.java) { update("Mood", " ") }
        assertThrows(IllegalStateException::class.java) { create("Mood", "Mood") }
        assertThrows(IllegalStateException::class.java) { create("Mood", " ") }
        create("Mood", "Sleep")
        update("Mood", "Sleep")
        QuestionnaireUpdate("Survey", null, null, true, null)
    }
}
