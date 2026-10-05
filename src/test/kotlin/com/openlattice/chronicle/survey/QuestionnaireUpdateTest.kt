package com.openlattice.chronicle.survey

import org.junit.Assert.assertThrows
import org.junit.Test

class QuestionnaireUpdateTest {
    private fun update(vararg titles: String) =
        QuestionnaireUpdate("Survey", null, null, null, titles.map { Question(it) })

    @Test
    fun `update rejects question titles that create rejects`() {
        assertThrows(IllegalStateException::class.java) { update("Mood", "Mood") }
        assertThrows(IllegalStateException::class.java) { update("Mood", " ") }
        update("Mood", "Sleep")
        QuestionnaireUpdate("Survey", null, null, true, null)
    }
}
