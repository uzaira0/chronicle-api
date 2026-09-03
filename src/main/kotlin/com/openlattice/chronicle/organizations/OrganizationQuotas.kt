package com.openlattice.chronicle.organizations

import java.util.*

public data class OrganizationQuotas(
    val organizationId: UUID = UUID(0, 0),
    val maxStudies: Int = 100,
    val maxParticipantsPerStudy: Int = 10000,
    val maxApiKeysPerStudy: Int = 20,
    val maxWebhooksPerStudy: Int = 10
)
