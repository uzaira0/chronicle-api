package com.openlattice.chronicle.study

import java.time.OffsetDateTime

/** One retained diagnostic code aggregated within a participant, device, and day history row. */
public data class AndroidDiagnosticCodeSummary(
    val eventId: String,
    val moduleFamily: String,
    val issueCode: String,
    val occurrenceCount: Long,
    val firstOccurredAt: OffsetDateTime,
    val lastOccurredAt: OffsetDateTime,
    val httpStatus: Int?,
    val errorType: String?,
)
