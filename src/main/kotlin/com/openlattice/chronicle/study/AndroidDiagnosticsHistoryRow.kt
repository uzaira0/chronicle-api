package com.openlattice.chronicle.study

import java.time.LocalDate
import java.util.UUID

/** A day's diagnostic buckets for one device, or one redacted data-quality alert. */
public data class AndroidDiagnosticsHistoryRow(
    val participantId: String,
    val deviceId: UUID?,
    val day: LocalDate,
    val codes: List<AndroidDiagnosticCodeSummary> = emptyList(),
    val dataQualityAlert: DataQualityAlertHistoryItem? = null,
)
