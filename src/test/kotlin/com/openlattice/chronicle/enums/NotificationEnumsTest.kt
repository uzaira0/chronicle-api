package com.openlattice.chronicle.enums

import com.openlattice.chronicle.notifications.DeliveryType
import com.openlattice.chronicle.notifications.NotificationStatus
import com.openlattice.chronicle.notifications.NotificationType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class NotificationEnumsTest {

    // ===== DeliveryType (2 values) =====
    @Test fun testDeliveryTypeCount() { assertEquals(2, DeliveryType.values().size) }
    @Test fun testDeliveryTypeSms() { assertEquals("SMS", DeliveryType.SMS.name) }
    @Test fun testDeliveryTypeEmail() { assertEquals("EMAIL", DeliveryType.EMAIL.name) }
    @Test fun testDeliveryTypeOrdinalSms() { assertEquals(0, DeliveryType.SMS.ordinal) }
    @Test fun testDeliveryTypeOrdinalEmail() { assertEquals(1, DeliveryType.EMAIL.ordinal) }
    @Test fun testDeliveryTypeValueOfSms() { assertEquals(DeliveryType.SMS, DeliveryType.valueOf("SMS")) }
    @Test fun testDeliveryTypeValueOfEmail() { assertEquals(DeliveryType.EMAIL, DeliveryType.valueOf("EMAIL")) }
    @Test fun testDeliveryTypeValueOfInvalid() {
        try {
            DeliveryType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== NotificationStatus (5 values) =====
    @Test fun testNotificationStatusCount() { assertEquals(5, NotificationStatus.values().size) }
    @Test fun testNotificationStatusDelivered() { assertEquals("delivered", NotificationStatus.delivered.name) }
    @Test fun testNotificationStatusDeliveryUnknown() { assertEquals("delivery_unknown", NotificationStatus.delivery_unknown.name) }
    @Test fun testNotificationStatusFailed() { assertEquals("failed", NotificationStatus.failed.name) }
    @Test fun testNotificationStatusSent() { assertEquals("sent", NotificationStatus.sent.name) }
    @Test fun testNotificationStatusUndelivered() { assertEquals("undelivered", NotificationStatus.undelivered.name) }
    @Test fun testNotificationStatusOrdinalDelivered() { assertEquals(0, NotificationStatus.delivered.ordinal) }
    @Test fun testNotificationStatusOrdinalDeliveryUnknown() { assertEquals(1, NotificationStatus.delivery_unknown.ordinal) }
    @Test fun testNotificationStatusOrdinalFailed() { assertEquals(2, NotificationStatus.failed.ordinal) }
    @Test fun testNotificationStatusOrdinalSent() { assertEquals(3, NotificationStatus.sent.ordinal) }
    @Test fun testNotificationStatusOrdinalUndelivered() { assertEquals(4, NotificationStatus.undelivered.ordinal) }
    @Test fun testNotificationStatusValueOfDelivered() { assertEquals(NotificationStatus.delivered, NotificationStatus.valueOf("delivered")) }
    @Test fun testNotificationStatusValueOfInvalid() {
        try {
            NotificationStatus.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== NotificationType (5 values) =====
    @Test fun testNotificationTypeCount() { assertEquals(5, NotificationType.values().size) }
    @Test fun testNotificationTypeAuditEvent() { assertEquals("AUDIT_EVENT", NotificationType.AUDIT_EVENT.name) }
    @Test fun testNotificationTypePassiveDataCompliace() {
        assertEquals("PASSIVE_DATA_COLLECTION_COMPLIANCE", NotificationType.PASSIVE_DATA_COLLECTION_COMPLIANCE.name)
    }
    @Test fun testNotificationTypeTudSubmission() { assertEquals("TUD_SUBMISSION_COMPLIANCE", NotificationType.TUD_SUBMISSION_COMPLIANCE.name) }
    @Test fun testNotificationTypeOperationalChecks() { assertEquals("OPERATIONAL_CHECKS", NotificationType.OPERATIONAL_CHECKS.name) }
    @Test fun testNotificationTypeEnrollment() { assertEquals("ENROLLMENT", NotificationType.ENROLLMENT.name) }
    @Test fun testNotificationTypeOrdinalAuditEvent() { assertEquals(0, NotificationType.AUDIT_EVENT.ordinal) }
    @Test fun testNotificationTypeOrdinalPassiveData() { assertEquals(1, NotificationType.PASSIVE_DATA_COLLECTION_COMPLIANCE.ordinal) }
    @Test fun testNotificationTypeOrdinalTud() { assertEquals(2, NotificationType.TUD_SUBMISSION_COMPLIANCE.ordinal) }
    @Test fun testNotificationTypeOrdinalOperational() { assertEquals(3, NotificationType.OPERATIONAL_CHECKS.ordinal) }
    @Test fun testNotificationTypeOrdinalEnrollment() { assertEquals(4, NotificationType.ENROLLMENT.ordinal) }
    @Test fun testNotificationTypeValueOfEnrollment() { assertEquals(NotificationType.ENROLLMENT, NotificationType.valueOf("ENROLLMENT")) }
    @Test fun testNotificationTypeValueOfInvalid() {
        try {
            NotificationType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
