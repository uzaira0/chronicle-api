package com.openlattice.chronicle.study

import java.time.OffsetDateTime

/**
 * Data an Android device discarded locally, reported as a count through upload diagnostics
 * (last 30 days): sensor samples expired by age or dropped at the row cap, dead-lettered sensor
 * samples dropped at their cap, and usage rows evicted while device storage was low.
 */
public data class AndroidDataDrop(
    val issueCode: String,
    val count: Long,
    val lastOccurredAt: OffsetDateTime,
)
