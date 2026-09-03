package com.openlattice.chronicle.models

import com.openlattice.chronicle.notifications.DeliveryType
import com.openlattice.chronicle.notifications.NotificationType
import com.openlattice.chronicle.notifications.ParticipantNotification
import com.openlattice.chronicle.notifications.StudyNotificationSettings
import com.openlattice.chronicle.study.StudyDuration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.*

class NotificationModelsTest {

    // ===== ParticipantNotification =====
    @Test fun testParticipantNotificationConstruction() {
        val pn = ParticipantNotification(
            participantId = "p001",
            notificationType = NotificationType.ENROLLMENT,
            deliveryType = EnumSet.of(DeliveryType.SMS),
            message = "Welcome!"
        )
        assertEquals("p001", pn.participantId)
        assertEquals(NotificationType.ENROLLMENT, pn.notificationType)
        assertTrue(pn.deliveryType.contains(DeliveryType.SMS))
        assertEquals("Welcome!", pn.message)
    }
    @Test fun testParticipantNotificationDateTimeDefault() {
        val pn = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.EMAIL), "msg")
        assertNotNull(pn.dateTime)
    }
    @Test fun testParticipantNotificationMultipleDeliveryTypes() {
        val pn = ParticipantNotification("p1", NotificationType.AUDIT_EVENT, EnumSet.of(DeliveryType.SMS, DeliveryType.EMAIL), "msg")
        assertEquals(2, pn.deliveryType.size)
    }
    @Test fun testParticipantNotificationEquality() {
        val dt = OffsetDateTime.now()
        val pn1 = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.SMS), "msg", dt)
        val pn2 = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.SMS), "msg", dt)
        assertEquals(pn1, pn2)
    }
    @Test fun testParticipantNotificationInequality() {
        val pn1 = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.SMS), "msg1", OffsetDateTime.now())
        val pn2 = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.SMS), "msg2", pn1.dateTime)
        assertNotEquals(pn1, pn2)
    }
    @Test fun testParticipantNotificationToString() {
        val pn = ParticipantNotification("p1", NotificationType.ENROLLMENT, EnumSet.of(DeliveryType.SMS), "msg")
        assertNotNull(pn.toString())
    }

    // ===== StudyNotificationSettings =====
    @Test fun testStudyNotificationSettingsConstruction() {
        val s = StudyNotificationSettings(labFriendlyName = "Lab X", studyFriendlyName = "Study A")
        assertEquals("Lab X", s.labFriendlyName)
        assertEquals("Study A", s.studyFriendlyName)
    }
    @Test fun testStudyNotificationSettingsDefaultNotifyResearchers() {
        val s = StudyNotificationSettings("L", "S")
        assertFalse(s.notifyResearchers)
    }
    @Test fun testStudyNotificationSettingsDefaultNotifyOnEnrollment() {
        val s = StudyNotificationSettings("L", "S")
        assertFalse(s.notifyOnEnrollment)
    }
    @Test fun testStudyNotificationSettingsDefaultResearcherPhoneNumbers() {
        val s = StudyNotificationSettings("L", "S")
        assertEquals("", s.researcherPhoneNumbers)
    }
    @Test fun testStudyNotificationSettingsDefaultNoDataUploaded() {
        val s = StudyNotificationSettings("L", "S")
        assertEquals(StudyDuration(days = 1), s.noDataUploaded)
    }
    @Test fun testStudyNotificationSettingsDefaultNoTudSubmitted() {
        val s = StudyNotificationSettings("L", "S")
        assertEquals(StudyDuration(days = 1), s.noTudSubmitted)
    }
    @Test fun testStudyNotificationSettingsDefaultNoAppUsageSurveySubmitted() {
        val s = StudyNotificationSettings("L", "S")
        assertEquals(StudyDuration(days = 1), s.noAppUsageSurveySubmitted)
    }
    @Test fun testStudyNotificationSettingsEnrollmentMessageWithLab() {
        val s = StudyNotificationSettings(labFriendlyName = "BCM", studyFriendlyName = "SleepStudy")
        val msg = s.getEnrollmentMessage()
        assertTrue(msg.contains("BCM"))
        assertTrue(msg.contains("SleepStudy"))
    }
    @Test fun testStudyNotificationSettingsEnrollmentMessageWithoutLab() {
        val s = StudyNotificationSettings(labFriendlyName = "", studyFriendlyName = "SleepStudy")
        val msg = s.getEnrollmentMessage()
        assertTrue(msg.contains("SleepStudy"))
        assertFalse(msg.contains("by  to"))
    }
    @Test fun testStudyNotificationSettingsEquality() {
        assertEquals(
            StudyNotificationSettings("L", "S"),
            StudyNotificationSettings("L", "S")
        )
    }
    @Test fun testStudyNotificationSettingsInequality() {
        assertNotEquals(
            StudyNotificationSettings("L1", "S"),
            StudyNotificationSettings("L2", "S")
        )
    }
    @Test fun testStudyNotificationSettingsCustomNotifyResearchers() {
        val s = StudyNotificationSettings("L", "S", notifyResearchers = true)
        assertTrue(s.notifyResearchers)
    }
    @Test fun testStudyNotificationSettingsToString() {
        assertNotNull(StudyNotificationSettings("L", "S").toString())
    }
}
