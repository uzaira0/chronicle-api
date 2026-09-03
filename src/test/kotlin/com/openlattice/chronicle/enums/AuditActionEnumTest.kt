package com.openlattice.chronicle.enums

import com.openlattice.chronicle.audit.AuditAction
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class AuditActionEnumTest {

    @Test fun testAuditActionCount() { assertEquals(45, AuditAction.values().size) }

    // Authentication events
    @Test fun testAuditActionLogin() { assertEquals("LOGIN", AuditAction.LOGIN.name) }
    @Test fun testAuditActionLogout() { assertEquals("LOGOUT", AuditAction.LOGOUT.name) }
    @Test fun testAuditActionLoginFailed() { assertEquals("LOGIN_FAILED", AuditAction.LOGIN_FAILED.name) }
    @Test fun testAuditActionTokenRefresh() { assertEquals("TOKEN_REFRESH", AuditAction.TOKEN_REFRESH.name) }
    @Test fun testAuditActionSessionExpired() { assertEquals("SESSION_EXPIRED", AuditAction.SESSION_EXPIRED.name) }

    // Data access events
    @Test fun testAuditActionView() { assertEquals("VIEW", AuditAction.VIEW.name) }
    @Test fun testAuditActionSearch() { assertEquals("SEARCH", AuditAction.SEARCH.name) }
    @Test fun testAuditActionList() { assertEquals("LIST", AuditAction.LIST.name) }
    @Test fun testAuditActionExport() { assertEquals("EXPORT", AuditAction.EXPORT.name) }
    @Test fun testAuditActionDownload() { assertEquals("DOWNLOAD", AuditAction.DOWNLOAD.name) }

    // Data modification events
    @Test fun testAuditActionCreate() { assertEquals("CREATE", AuditAction.CREATE.name) }
    @Test fun testAuditActionUpdate() { assertEquals("UPDATE", AuditAction.UPDATE.name) }
    @Test fun testAuditActionDelete() { assertEquals("DELETE", AuditAction.DELETE.name) }
    @Test fun testAuditActionArchive() { assertEquals("ARCHIVE", AuditAction.ARCHIVE.name) }
    @Test fun testAuditActionRestore() { assertEquals("RESTORE", AuditAction.RESTORE.name) }

    // Permission events
    @Test fun testAuditActionPermissionChange() { assertEquals("PERMISSION_CHANGE", AuditAction.PERMISSION_CHANGE.name) }
    @Test fun testAuditActionPermissionGrant() { assertEquals("PERMISSION_GRANT", AuditAction.PERMISSION_GRANT.name) }
    @Test fun testAuditActionPermissionRevoke() { assertEquals("PERMISSION_REVOKE", AuditAction.PERMISSION_REVOKE.name) }
    @Test fun testAuditActionSettingsChange() { assertEquals("SETTINGS_CHANGE", AuditAction.SETTINGS_CHANGE.name) }
    @Test fun testAuditActionConfigurationChange() { assertEquals("CONFIGURATION_CHANGE", AuditAction.CONFIGURATION_CHANGE.name) }

    // Security events
    @Test fun testAuditActionUnauthorizedAccess() { assertEquals("UNAUTHORIZED_ACCESS", AuditAction.UNAUTHORIZED_ACCESS.name) }
    @Test fun testAuditActionAccessDenied() { assertEquals("ACCESS_DENIED", AuditAction.ACCESS_DENIED.name) }
    @Test fun testAuditActionInvalidRequest() { assertEquals("INVALID_REQUEST", AuditAction.INVALID_REQUEST.name) }
    @Test fun testAuditActionRateLimited() { assertEquals("RATE_LIMITED", AuditAction.RATE_LIMITED.name) }
    @Test fun testAuditActionSuspiciousActivity() { assertEquals("SUSPICIOUS_ACTIVITY", AuditAction.SUSPICIOUS_ACTIVITY.name) }

    // Mobile API events
    @Test fun testAuditActionDataSubmission() { assertEquals("DATA_SUBMISSION", AuditAction.DATA_SUBMISSION.name) }
    @Test fun testAuditActionDeviceEnrollment() { assertEquals("DEVICE_ENROLLMENT", AuditAction.DEVICE_ENROLLMENT.name) }
    @Test fun testAuditActionDeviceUnenrollment() { assertEquals("DEVICE_UNENROLLMENT", AuditAction.DEVICE_UNENROLLMENT.name) }
    @Test fun testAuditActionSensorDataUpload() { assertEquals("SENSOR_DATA_UPLOAD", AuditAction.SENSOR_DATA_UPLOAD.name) }
    @Test fun testAuditActionUsageDataUpload() { assertEquals("USAGE_DATA_UPLOAD", AuditAction.USAGE_DATA_UPLOAD.name) }
    @Test fun testAuditActionBatteryTelemetryUpload() { assertEquals("BATTERY_TELEMETRY_UPLOAD", AuditAction.BATTERY_TELEMETRY_UPLOAD.name) }
    @Test fun testAuditActionInteractionEventsUpload() { assertEquals("INTERACTION_EVENTS_UPLOAD", AuditAction.INTERACTION_EVENTS_UPLOAD.name) }
    @Test fun testAuditActionCollectionAcknowledgment() { assertEquals("COLLECTION_ACKNOWLEDGMENT", AuditAction.COLLECTION_ACKNOWLEDGMENT.name) }

    // Study events
    @Test fun testAuditActionStudyCreate() { assertEquals("STUDY_CREATE", AuditAction.STUDY_CREATE.name) }
    @Test fun testAuditActionStudyUpdate() { assertEquals("STUDY_UPDATE", AuditAction.STUDY_UPDATE.name) }
    @Test fun testAuditActionStudyDelete() { assertEquals("STUDY_DELETE", AuditAction.STUDY_DELETE.name) }
    @Test fun testAuditActionParticipantEnroll() { assertEquals("PARTICIPANT_ENROLL", AuditAction.PARTICIPANT_ENROLL.name) }
    @Test fun testAuditActionParticipantWithdraw() { assertEquals("PARTICIPANT_WITHDRAW", AuditAction.PARTICIPANT_WITHDRAW.name) }
    @Test fun testAuditActionParticipantDataAccess() { assertEquals("PARTICIPANT_DATA_ACCESS", AuditAction.PARTICIPANT_DATA_ACCESS.name) }

    // Notification events
    @Test fun testAuditActionNotificationSent() { assertEquals("NOTIFICATION_SENT", AuditAction.NOTIFICATION_SENT.name) }
    @Test fun testAuditActionNotificationFailed() { assertEquals("NOTIFICATION_FAILED", AuditAction.NOTIFICATION_FAILED.name) }

    // Job events
    @Test fun testAuditActionJobCreated() { assertEquals("JOB_CREATED", AuditAction.JOB_CREATED.name) }
    @Test fun testAuditActionJobCompleted() { assertEquals("JOB_COMPLETED", AuditAction.JOB_COMPLETED.name) }
    @Test fun testAuditActionJobFailed() { assertEquals("JOB_FAILED", AuditAction.JOB_FAILED.name) }
    @Test fun testAuditActionDataDeletion() { assertEquals("DATA_DELETION", AuditAction.DATA_DELETION.name) }

    // valueOf tests
    @Test fun testAuditActionValueOfLogin() { assertEquals(AuditAction.LOGIN, AuditAction.valueOf("LOGIN")) }
    @Test fun testAuditActionValueOfInvalid() {
        try {
            AuditAction.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // Ordinal spot-checks
    @Test fun testAuditActionOrdinalLogin() { assertEquals(0, AuditAction.LOGIN.ordinal) }
    @Test fun testAuditActionOrdinalLogout() { assertEquals(1, AuditAction.LOGOUT.ordinal) }
    @Test fun testAuditActionOrdinalDataDeletion() { assertEquals(44, AuditAction.DATA_DELETION.ordinal) }
}
