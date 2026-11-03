package com.openlattice.chronicle.survey

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.valueextraction.Unwrapping
import org.dmfs.rfc5545.recur.RecurrenceRule
import java.util.Optional

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public data class QuestionnaireUpdate(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 255, message = "Title exceeds maximum length")
    val title: String,

    @field:Size(max = 4000, message = "Description exceeds maximum length")
    val description: String?,

    /** Omitted = unchanged; JSON null / Optional.empty = clear; a rule = replace. */
    @field:Size(max = 500, message = "Recurrence rule exceeds maximum length", payload = [Unwrapping.Unwrap::class])
    var recurrenceRule: Optional<String>? = null,

    val active: Boolean?,

    @field:Valid
    val questions: List<Question>?
) {
    init {
        // Responses are keyed by question title, so an update must keep the same title rules as create.
        questions?.let {
            check(it.isNotEmpty()) { "Questionnaire must contain at least one question" }
            validateQuestionTitles(it)
        }
        recurrenceRule = recurrenceRule?.map { RecurrenceRule(it).toString() }
    }
}
