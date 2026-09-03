package com.openlattice.chronicle.timeusediary

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 *
 */

public data class TimeUseDiaryResponse(

        @field:NotBlank(message = "Code is required")
        @field:Size(max = 100, message = "Code exceeds maximum length")
        @param:JsonProperty("ol.code")
        @param:JsonAlias("code")
        val code: String,

        @field:NotBlank(message = "Question is required")
        @field:Size(max = 1000, message = "Question exceeds maximum length")
        @param:JsonProperty("ol.title")
        @param:JsonAlias("question")
        val question: String,

        @field:NotNull(message = "Response is required")
        @field:Size(max = 100, message = "Response has too many values")
        @param:JsonProperty("ol.values")
        @param:JsonAlias("response")
        val response: Set<@Size(max = 1000, message = "Response value exceeds maximum length") String>,

        @param:JsonProperty("ol.datetimestart")
        @param:JsonAlias("startDateTime")
        val startDateTime: OffsetDateTime?,

        @param:JsonProperty("ol.datetimeend")
        @param:JsonAlias("endDateTime")
        val endDateTime: OffsetDateTime?
)
