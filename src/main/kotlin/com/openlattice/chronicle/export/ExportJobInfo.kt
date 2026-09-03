package com.openlattice.chronicle.export

import java.time.OffsetDateTime
import java.util.*

public data class ExportJobInfo(
    val exportId: UUID,
    val studyId: UUID,
    val status: ExportJobStatus,
    val format: ExportFormat,
    val createdAt: OffsetDateTime,
    val completedAt: OffsetDateTime? = null,
    val downloadToken: String? = null,
    val rowCount: Long = 0,
    val errorMessage: String? = null,
    @com.fasterxml.jackson.annotation.JsonIgnore
    val filePath: String? = null
)
