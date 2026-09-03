package com.openlattice.chronicle.dashboard

import java.time.OffsetDateTime
import java.util.*

public data class StudyEvent(
    val eventId: UUID = UUID.randomUUID(),
    val studyId: UUID,
    val eventType: String,
    val participantId: String? = null,
    val metadata: Map<String, Any> = emptyMap(),
    val createdAt: OffsetDateTime = OffsetDateTime.now()
)
