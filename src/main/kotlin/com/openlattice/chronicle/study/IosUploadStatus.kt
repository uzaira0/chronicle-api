package com.openlattice.chronicle.study

import java.time.OffsetDateTime

/**
 * Server-observed iOS upload state for one study participant.
 */
public data class IosUploadStatus(
    val participantId: String,
    val committedRows: Long = 0,
    val lastCommittedAt: OffsetDateTime? = null,
    val lastObservationEndAt: OffsetDateTime? = null,
    val bufferedBatches: Long = 0,
    val bufferedRecords: Long = 0,
    val lastBufferedUploadAt: OffsetDateTime? = null,
)
