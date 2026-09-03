package com.openlattice.chronicle.timeusediary

import com.openlattice.chronicle.ids.IdConstants
import java.util.*

/**
 * @author Andrew Carter andrew@openlattice.com
 */
public data class TimeUseDiary(
    val id: UUID = IdConstants.UNINITIALIZED.id,
    val organizationId: UUID,
    val studyId: UUID,
    val participantId: UUID,
    val responses: TimeUseDiaryResponse
)
