package com.openlattice.chronicle.study

import java.time.OffsetDateTime

/**
 * Server-observed iOS Screen Time upload state for one study participant: stored rows, the newest
 * capture instant, and the end of the latest observation window.
 */
public data class IosUploadStatus(
    val participantId: String,
    val committedRows: Long = 0,
    val lastCommittedAt: OffsetDateTime? = null,
    val lastObservationEndAt: OffsetDateTime? = null,
)
