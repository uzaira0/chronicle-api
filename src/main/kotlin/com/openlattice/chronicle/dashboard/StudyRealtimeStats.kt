package com.openlattice.chronicle.dashboard

import java.time.OffsetDateTime
import java.util.*

public data class StudyRealtimeStats(
    val studyId: UUID,
    val activeParticipants24h: Int = 0,
    val dataSubmissions24h: Long = 0,
    val totalParticipants: Int = 0,
    val lastDataReceived: OffsetDateTime? = null,
    val submissionsByType: Map<String, Long> = emptyMap(),
    val timestamp: OffsetDateTime = OffsetDateTime.now()
)
