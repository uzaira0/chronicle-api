package com.openlattice.chronicle.study

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.Size
import jakarta.validation.valueextraction.Unwrapping
import java.util.Optional

/** Null wrapper means omitted; Optional.empty means clear; Optional.of means replace. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public data class ParticipantAnnotationsUpdate(
    @field:Size(max = 50_000, message = "Participant notes exceed maximum length", payload = [Unwrapping.Unwrap::class])
    val participantNotes: Optional<String>? = null,
    val participantTags: Optional<@Size(max = 1000, message = "Participant has too many tags") List<
        @Size(max = 255, message = "Participant tag exceeds maximum length") String>>? = null,
) {
    init {
        val tags: List<*>? = participantTags?.orElse(null)
        require(tags?.all { it is String } != false) {
            "Participant tags must contain only strings"
        }
    }
}
