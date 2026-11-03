package com.openlattice.chronicle.study

import java.util.*

public data class StudyCompliancePage(
    val violations: Map<UUID, Map<String, List<ComplianceViolation>>>,
    val nextOffset: Long?,
)

public interface StudyComplianceManager {
    public fun getStudyCompliancePage(studyId: UUID, limit: Int, offset: Int): StudyCompliancePage
    public fun getNonCompliantStudies( studies: Collection<UUID>) : Map<UUID, Map<String, List<ComplianceViolation>>>
    public fun getAllNonCompliantStudies() : Map<UUID, Map<String, List<ComplianceViolation>>>
}
