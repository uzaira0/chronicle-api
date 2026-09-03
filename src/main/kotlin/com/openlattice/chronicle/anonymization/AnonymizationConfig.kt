package com.openlattice.chronicle.anonymization

import com.fasterxml.jackson.annotation.JsonProperty

public data class AnonymizationConfig(
    val pseudonymizeParticipantIds: Boolean = true,
    val redactedFields: Set<String> = emptySet(),
    @param:JsonProperty("kAnonymityThreshold")
    @get:JsonProperty("kAnonymityThreshold")
    val kAnonymityThreshold: Int = 5,
    val dateGeneralization: DateGeneralization = DateGeneralization.DAY
)

public enum class DateGeneralization {
    NONE,
    DAY,
    WEEK,
    MONTH
}
