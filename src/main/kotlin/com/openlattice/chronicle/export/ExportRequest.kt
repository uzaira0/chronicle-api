package com.openlattice.chronicle.export

import com.openlattice.chronicle.study.ParticipantDataType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime

/**
 * Request to export study data asynchronously.
 */
public data class ExportRequest(
    @field:NotEmpty(message = "At least one data type is required")
    val dataTypes: Set<ParticipantDataType> = emptySet(),

    /** Empty means every participant in the study. */
    val participantIds: Set<String> = emptySet(),

    /** Null means the beginning of retained history. */
    val startDate: OffsetDateTime? = null,

    /** Null means the end of retained history. */
    val endDate: OffsetDateTime? = null,

    @field:NotNull(message = "Export format is required")
    val format: ExportFormat = ExportFormat.CSV
)
