package com.openlattice.chronicle.study

import java.time.OffsetDateTime
import java.util.UUID

/** Redacted historical projection; alert message text is intentionally not exposed. */
public data class DataQualityAlertHistoryItem(
    val alertId: UUID,
    val alertType: String,
    val score: Double,
    val createdAt: OffsetDateTime,
)
