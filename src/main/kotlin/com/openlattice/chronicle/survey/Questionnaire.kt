package com.openlattice.chronicle.survey

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.dmfs.rfc5545.recur.RecurrenceRule
import java.time.OffsetDateTime
import java.util.*

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public data class Questionnaire(
    val id: UUID?,

    @field:NotBlank(message = "Title is required")
    @field:Size(max = 255, message = "Title exceeds maximum length")
    val title: String,

    val dateCreated: OffsetDateTime?,

    @field:Size(max = 4000, message = "Description exceeds maximum length")
    val description: String = "",

    val active: Boolean = true,

    @field:Valid
    @field:NotEmpty(message = "Questions are required")
    @field:Size(max = 100, message = "Questionnaire has too many questions")
    val questions: List<Question>,

    @field:Size(max = 500, message = "Recurrence rule exceeds maximum length")
    var recurrenceRule: String?
) {
    init {
        check(questions.isNotEmpty()) { "questions must be non-empty" }
        check(title.isNotBlank()) { "title cannot be blank" }
        val questionTitles = questions.map { it.title }
        check(questionTitles.none { it.isBlank() }) { "question titles cannot be blank" }
        check(questionTitles.distinct().size == questionTitles.size) { "question titles should be unique" }

        // for multiple choice questions, ensure that choices are unique
        questions.forEach {
            check(it.choices.distinct().size == it.choices.size) { "choices for multiple-choice questions should be unique" }
        }

        // recurrence
        recurrenceRule?.let {
            recurrenceRule = RecurrenceRule(it).toString()
        }
    }
}

public data class Question(
    @field:NotBlank(message = "Question title is required")
    @field:Size(max = 1000, message = "Question title exceeds maximum length")
    val title: String,

    @field:Size(max = 100, message = "Question has too many choices")
    val choices: Set<@Size(max = 1000, message = "Choice exceeds maximum length") String> = setOf()
)

public data class QuestionnaireResponse(
    @field:NotBlank(message = "Question title is required")
    @field:Size(max = 1000, message = "Question title exceeds maximum length")
    val questionTitle: String,

    @field:NotNull(message = "Response value is required")
    @field:Size(max = 100, message = "Response has too many values")
    val value: Set<@Size(max = 4000, message = "Response value exceeds maximum length") String>
) {
    init {
        check(questionTitle.isNotBlank()) { "question title must be non-blank" }
    }
}
