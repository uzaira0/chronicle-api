package com.openlattice.chronicle.models

import com.openlattice.chronicle.audit.AuditAction
import com.openlattice.chronicle.audit.AuditLogEntry
import com.openlattice.chronicle.audit.AuditLogEntryBuilder
import com.openlattice.chronicle.audit.auditLogEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class AuditModelsTest {

    // ===== AuditLogEntry =====
    @Test fun testAuditLogEntryConstruction() {
        val entry = AuditLogEntry(
            ipAddress = "192.168.1.1",
            action = AuditAction.LOGIN,
            resourceType = "Study",
            success = true
        )
        assertNotNull(entry.id)
        assertNotNull(entry.timestamp)
        assertEquals("192.168.1.1", entry.ipAddress)
        assertEquals(AuditAction.LOGIN, entry.action)
        assertEquals("Study", entry.resourceType)
        assertTrue(entry.success)
    }
    @Test fun testAuditLogEntryDefaultId() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNotNull(entry.id)
    }
    @Test fun testAuditLogEntryDefaultTimestamp() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNotNull(entry.timestamp)
    }
    @Test fun testAuditLogEntryDefaultUserId() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.userId)
    }
    @Test fun testAuditLogEntryDefaultUserRole() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.userRole)
    }
    @Test fun testAuditLogEntryDefaultUserAgent() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.userAgent)
    }
    @Test fun testAuditLogEntryDefaultResourceId() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.resourceId)
    }
    @Test fun testAuditLogEntryDefaultStudyId() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.studyId)
    }
    @Test fun testAuditLogEntryDefaultOrganizationId() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.organizationId)
    }
    @Test fun testAuditLogEntryDefaultAccessedPHI() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertFalse(entry.accessedPHI)
    }
    @Test fun testAuditLogEntryDefaultPhiFields() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.phiFields)
    }
    @Test fun testAuditLogEntryDefaultRequestPath() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.requestPath)
    }
    @Test fun testAuditLogEntryDefaultRequestMethod() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.requestMethod)
    }
    @Test fun testAuditLogEntryDefaultResponseCode() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.responseCode)
    }
    @Test fun testAuditLogEntryDefaultDurationMs() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.durationMs)
    }
    @Test fun testAuditLogEntryDefaultAdditionalData() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertNull(entry.additionalData)
    }

    // isFailed()
    @Test fun testIsFailedTrue() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = false)
        assertTrue(entry.isFailed())
    }
    @Test fun testIsFailedFalse() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertFalse(entry.isFailed())
    }

    // involvesPHI()
    @Test fun testInvolvesPHITrueFromFlag() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true, accessedPHI = true)
        assertTrue(entry.involvesPHI())
    }
    @Test fun testInvolvesPHITrueFromFields() {
        val entry = AuditLogEntry(
            ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X",
            success = true, phiFields = listOf("firstName")
        )
        assertTrue(entry.involvesPHI())
    }
    @Test fun testInvolvesPHIFalse() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertFalse(entry.involvesPHI())
    }

    // isSecurityEvent()
    @Test fun testIsSecurityEventLogin() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.LOGIN, resourceType = "X", success = true)
        assertTrue(entry.isSecurityEvent())
    }
    @Test fun testIsSecurityEventLogout() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.LOGOUT, resourceType = "X", success = true)
        assertTrue(entry.isSecurityEvent())
    }
    @Test fun testIsSecurityEventLoginFailed() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.LOGIN_FAILED, resourceType = "X", success = false)
        assertTrue(entry.isSecurityEvent())
    }
    @Test fun testIsSecurityEventUnauthorized() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.UNAUTHORIZED_ACCESS, resourceType = "X", success = false)
        assertTrue(entry.isSecurityEvent())
    }
    @Test fun testIsSecurityEventFalseForView() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertFalse(entry.isSecurityEvent())
    }

    // isDataModification()
    @Test fun testIsDataModificationCreate() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.CREATE, resourceType = "X", success = true)
        assertTrue(entry.isDataModification())
    }
    @Test fun testIsDataModificationUpdate() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.UPDATE, resourceType = "X", success = true)
        assertTrue(entry.isDataModification())
    }
    @Test fun testIsDataModificationDelete() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.DELETE, resourceType = "X", success = true)
        assertTrue(entry.isDataModification())
    }
    @Test fun testIsDataModificationArchive() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.ARCHIVE, resourceType = "X", success = true)
        assertTrue(entry.isDataModification())
    }
    @Test fun testIsDataModificationFalseForView() {
        val entry = AuditLogEntry(ipAddress = "1.2.3.4", action = AuditAction.VIEW, resourceType = "X", success = true)
        assertFalse(entry.isDataModification())
    }

    // Companion object
    @Test fun testPhiFieldsNotEmpty() { assertTrue(AuditLogEntry.PHI_FIELDS.isNotEmpty()) }
    @Test fun testPhiFieldsContainsFirstName() { assertTrue(AuditLogEntry.PHI_FIELDS.contains("firstName")) }
    @Test fun testPhiFieldsContainsEmail() { assertTrue(AuditLogEntry.PHI_FIELDS.contains("email")) }
    @Test fun testPhiFieldsContainsDateOfBirth() { assertTrue(AuditLogEntry.PHI_FIELDS.contains("dateOfBirth")) }
    @Test fun testPhiResourceTypesNotEmpty() { assertTrue(AuditLogEntry.PHI_RESOURCE_TYPES.isNotEmpty()) }
    @Test fun testPhiResourceTypesContainsParticipant() { assertTrue(AuditLogEntry.PHI_RESOURCE_TYPES.contains("Participant")) }
    @Test fun testPhiResourceTypesContainsCandidate() { assertTrue(AuditLogEntry.PHI_RESOURCE_TYPES.contains("Candidate")) }

    // ===== AuditLogEntryBuilder =====
    @Test fun testBuilderMinimal() {
        val entry = AuditLogEntryBuilder()
            .ipAddress("10.0.0.1")
            .action(AuditAction.VIEW)
            .resourceType("Study")
            .success(true)
            .build()
        assertEquals("10.0.0.1", entry.ipAddress)
        assertEquals(AuditAction.VIEW, entry.action)
        assertEquals("Study", entry.resourceType)
        assertTrue(entry.success)
    }
    @Test fun testBuilderMissingIpAddress() {
        try {
            AuditLogEntryBuilder().action(AuditAction.VIEW).resourceType("X").success(true).build()
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testBuilderMissingAction() {
        try {
            AuditLogEntryBuilder().ipAddress("1.1.1.1").resourceType("X").success(true).build()
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testBuilderMissingResourceType() {
        try {
            AuditLogEntryBuilder().ipAddress("1.1.1.1").action(AuditAction.VIEW).success(true).build()
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testBuilderMissingSuccess() {
        try {
            AuditLogEntryBuilder().ipAddress("1.1.1.1").action(AuditAction.VIEW).resourceType("X").build()
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testBuilderFailed() {
        val entry = AuditLogEntryBuilder()
            .ipAddress("10.0.0.1")
            .action(AuditAction.LOGIN_FAILED)
            .resourceType("Auth")
            .failed("Bad password")
            .build()
        assertFalse(entry.success)
        assertEquals("Bad password", entry.errorMessage)
    }
    @Test fun testBuilderPhiField() {
        val entry = AuditLogEntryBuilder()
            .ipAddress("10.0.0.1")
            .action(AuditAction.VIEW)
            .resourceType("Participant")
            .success(true)
            .phiField("firstName")
            .build()
        assertTrue(entry.accessedPHI)
        assertTrue(entry.phiFields!!.contains("firstName"))
    }
    @Test fun testBuilderAdditionalData() {
        val entry = AuditLogEntryBuilder()
            .ipAddress("10.0.0.1")
            .action(AuditAction.VIEW)
            .resourceType("Study")
            .success(true)
            .additionalData("key", "value")
            .build()
        assertEquals("value", entry.additionalData!!["key"])
    }

    // ===== auditLogEntry DSL =====
    @Test fun testAuditLogEntryDsl() {
        val entry = auditLogEntry {
            ipAddress("10.0.0.1")
            action(AuditAction.CREATE)
            resourceType("Study")
            success(true)
        }
        assertEquals(AuditAction.CREATE, entry.action)
        assertTrue(entry.success)
    }
}
