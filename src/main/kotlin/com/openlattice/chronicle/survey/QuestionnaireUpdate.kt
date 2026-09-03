package com.openlattice.chronicle.survey

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.dmfs.rfc5545.recur.RecurrenceRule

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */

public data class QuestionnaireUpdate(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 255, message = "Title exceeds maximum length")
    val title: String,

    @field:Size(max = 4000, message = "Description exceeds maximum length")
    val description: String?,

    @field:Size(max = 500, message = "Recurrence rule exceeds maximum length")
    var recurrenceRule: String?,

    val active: Boolean?,

    @field:Valid
    val questions: List<Question>?
) {
    init {
        recurrenceRule?.let {
            recurrenceRule = RecurrenceRule(it).toString()
        }
    }
}
