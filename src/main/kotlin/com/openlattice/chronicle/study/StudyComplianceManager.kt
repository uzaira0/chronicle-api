package com.openlattice.chronicle.study

import java.util.*

public interface StudyComplianceManager {
    public fun getNonCompliantStudies( studies: Collection<UUID>) : Map<UUID, Map<String, List<ComplianceViolation>>>
    public fun getAllNonCompliantStudies() : Map<UUID, Map<String, List<ComplianceViolation>>>
}
