package com.openlattice.chronicle.enums

import com.openlattice.chronicle.settings.AppComponent
import com.openlattice.chronicle.settings.AppUsageFrequency
import com.openlattice.chronicle.storage.ChronicleStorage
import com.openlattice.chronicle.webhooks.WebhookEventType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class SettingsEnumsTest {

    // ===== AppUsageFrequency (2 values) =====
    @Test fun testAppUsageFrequencyCount() { assertEquals(2, AppUsageFrequency.values().size) }
    @Test fun testAppUsageFrequencyDaily() { assertEquals("DAILY", AppUsageFrequency.DAILY.name) }
    @Test fun testAppUsageFrequencyHourly() { assertEquals("HOURLY", AppUsageFrequency.HOURLY.name) }
    @Test fun testAppUsageFrequencyOrdinalDaily() { assertEquals(0, AppUsageFrequency.DAILY.ordinal) }
    @Test fun testAppUsageFrequencyOrdinalHourly() { assertEquals(1, AppUsageFrequency.HOURLY.ordinal) }
    @Test fun testAppUsageFrequencyValueOfDaily() { assertEquals(AppUsageFrequency.DAILY, AppUsageFrequency.valueOf("DAILY")) }
    @Test fun testAppUsageFrequencyValueOfInvalid() {
        try {
            AppUsageFrequency.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== WebhookEventType (4 values) =====
    @Test fun testWebhookEventTypeCount() { assertEquals(4, WebhookEventType.values().size) }
    @Test fun testWebhookEventTypeParticipantEnrolled() { assertEquals("PARTICIPANT_ENROLLED", WebhookEventType.PARTICIPANT_ENROLLED.name) }
    @Test fun testWebhookEventTypeDataSubmitted() { assertEquals("DATA_SUBMITTED", WebhookEventType.DATA_SUBMITTED.name) }
    @Test fun testWebhookEventTypeStudyStatusChanged() { assertEquals("STUDY_STATUS_CHANGED", WebhookEventType.STUDY_STATUS_CHANGED.name) }
    @Test fun testWebhookEventTypeExportCompleted() { assertEquals("EXPORT_COMPLETED", WebhookEventType.EXPORT_COMPLETED.name) }
    @Test fun testWebhookEventTypeOrdinalParticipantEnrolled() { assertEquals(0, WebhookEventType.PARTICIPANT_ENROLLED.ordinal) }
    @Test fun testWebhookEventTypeOrdinalDataSubmitted() { assertEquals(1, WebhookEventType.DATA_SUBMITTED.ordinal) }
    @Test fun testWebhookEventTypeOrdinalStudyStatusChanged() { assertEquals(2, WebhookEventType.STUDY_STATUS_CHANGED.ordinal) }
    @Test fun testWebhookEventTypeOrdinalExportCompleted() { assertEquals(3, WebhookEventType.EXPORT_COMPLETED.ordinal) }
    @Test fun testWebhookEventTypeValueOfDataSubmitted() { assertEquals(WebhookEventType.DATA_SUBMITTED, WebhookEventType.valueOf("DATA_SUBMITTED")) }
    @Test fun testWebhookEventTypeValueOfInvalid() {
        try {
            WebhookEventType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== AppComponent (4 values) =====
    @Test fun testAppComponentCount() { assertEquals(4, AppComponent.values().size) }
    @Test fun testAppComponentChronicle() { assertEquals("CHRONICLE", AppComponent.CHRONICLE.name) }
    @Test fun testAppComponentChronicleDataCollection() { assertEquals("CHRONICLE_DATA_COLLECTION", AppComponent.CHRONICLE_DATA_COLLECTION.name) }
    @Test fun testAppComponentChronicleSurveys() { assertEquals("CHRONICLE_SURVEYS", AppComponent.CHRONICLE_SURVEYS.name) }
    @Test fun testAppComponentTimeUseDiary() { assertEquals("TIME_USE_DIARY", AppComponent.TIME_USE_DIARY.name) }
    @Test fun testAppComponentToStringChronicle() { assertEquals("chronicle", AppComponent.CHRONICLE.toString()) }
    @Test fun testAppComponentToStringChronicleDataCollection() {
        assertEquals("chronicle_data_collection", AppComponent.CHRONICLE_DATA_COLLECTION.toString())
    }
    @Test fun testAppComponentToStringChronicleSurveys() { assertEquals("chronicle_surveys", AppComponent.CHRONICLE_SURVEYS.toString()) }
    @Test fun testAppComponentToStringTimeUseDiary() { assertEquals("time_use_diary", AppComponent.TIME_USE_DIARY.toString()) }
    @Test fun testAppComponentFromStringChronicle() { assertEquals(AppComponent.CHRONICLE, AppComponent.fromString("chronicle")) }
    @Test fun testAppComponentFromStringDataCollection() {
        assertEquals(AppComponent.CHRONICLE_DATA_COLLECTION, AppComponent.fromString("chronicle_data_collection"))
    }
    @Test fun testAppComponentFromStringSurveys() { assertEquals(AppComponent.CHRONICLE_SURVEYS, AppComponent.fromString("chronicle_surveys")) }
    @Test fun testAppComponentFromStringTUD() { assertEquals(AppComponent.TIME_USE_DIARY, AppComponent.fromString("time_use_diary")) }
    @Test fun testAppComponentFromStringInvalid() {
        try {
            AppComponent.fromString("invalid")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testAppComponentOrdinalChronicle() { assertEquals(0, AppComponent.CHRONICLE.ordinal) }
    @Test fun testAppComponentOrdinalDataCollection() { assertEquals(1, AppComponent.CHRONICLE_DATA_COLLECTION.ordinal) }
    @Test fun testAppComponentOrdinalSurveys() { assertEquals(2, AppComponent.CHRONICLE_SURVEYS.ordinal) }
    @Test fun testAppComponentOrdinalTUD() { assertEquals(3, AppComponent.TIME_USE_DIARY.ordinal) }

    // ===== ChronicleStorage (3 values) =====
    @Test fun testChronicleStorageCount() { assertEquals(3, ChronicleStorage.values().size) }
    @Test fun testChronicleStoragePlatform() { assertEquals("PLATFORM", ChronicleStorage.PLATFORM.name) }
    @Test fun testChronicleStoragePlatformRead() { assertEquals("PLATFORM_READ", ChronicleStorage.PLATFORM_READ.name) }
    @Test fun testChronicleStorageChronicle() { assertEquals("CHRONICLE", ChronicleStorage.CHRONICLE.name) }
    @Test fun testChronicleStoragePlatformId() { assertEquals("default", ChronicleStorage.PLATFORM.id) }
    @Test fun testChronicleStoragePlatformReadId() { assertEquals("platform_read", ChronicleStorage.PLATFORM_READ.id) }
    @Test fun testChronicleStorageChronicleId() { assertEquals("chronicle", ChronicleStorage.CHRONICLE.id) }
    @Test fun testChronicleStorageOrdinalPlatform() { assertEquals(0, ChronicleStorage.PLATFORM.ordinal) }
    @Test fun testChronicleStorageOrdinalPlatformRead() { assertEquals(1, ChronicleStorage.PLATFORM_READ.ordinal) }
    @Test fun testChronicleStorageOrdinalChronicle() { assertEquals(2, ChronicleStorage.CHRONICLE.ordinal) }
    @Test fun testChronicleStorageValueOfChronicle() { assertEquals(ChronicleStorage.CHRONICLE, ChronicleStorage.valueOf("CHRONICLE")) }
    @Test fun testChronicleStorageValueOfInvalid() {
        try {
            ChronicleStorage.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
