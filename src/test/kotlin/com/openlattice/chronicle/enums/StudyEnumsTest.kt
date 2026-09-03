package com.openlattice.chronicle.enums

import com.openlattice.chronicle.study.ParticipantDataType
import com.openlattice.chronicle.study.StudyFeature
import com.openlattice.chronicle.study.StudyLifecycleStatus
import com.openlattice.chronicle.study.StudySettingType
import com.openlattice.chronicle.study.ViolationReason
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class StudyEnumsTest {

    // ===== StudyFeature (9 values) =====
    @Test fun testStudyFeatureValueCount() { assertEquals(9, StudyFeature.values().size) }
    @Test fun testStudyFeatureAppUsage() { assertEquals("APP_USAGE", StudyFeature.APP_USAGE.name) }
    @Test fun testStudyFeatureAppUsageSurvey() { assertEquals("APP_USAGE_SURVEY", StudyFeature.APP_USAGE_SURVEY.name) }
    @Test fun testStudyFeatureTimeUseDiary() { assertEquals("TIME_USE_DIARY", StudyFeature.TIME_USE_DIARY.name) }
    @Test fun testStudyFeatureArchive() { assertEquals("ARCHIVE", StudyFeature.ARCHIVE.name) }
    @Test fun testStudyFeatureIosSensor() { assertEquals("IOS_SENSOR", StudyFeature.IOS_SENSOR.name) }
    @Test fun testStudyFeatureAndroidSensor() { assertEquals("ANDROID_SENSOR", StudyFeature.ANDROID_SENSOR.name) }
    @Test fun testStudyFeatureChronicleSurveys() { assertEquals("CHRONICLE_SURVEYS", StudyFeature.CHRONICLE_SURVEYS.name) }
    @Test fun testStudyFeatureChronicle() { assertEquals("CHRONICLE", StudyFeature.CHRONICLE.name) }
    @Test fun testStudyFeatureChronicleDataCollection() { assertEquals("CHRONICLE_DATA_COLLECTION", StudyFeature.CHRONICLE_DATA_COLLECTION.name) }
    @Test fun testStudyFeatureOrdinalAppUsage() { assertEquals(0, StudyFeature.APP_USAGE.ordinal) }
    @Test fun testStudyFeatureOrdinalAppUsageSurvey() { assertEquals(1, StudyFeature.APP_USAGE_SURVEY.ordinal) }
    @Test fun testStudyFeatureOrdinalTimeUseDiary() { assertEquals(2, StudyFeature.TIME_USE_DIARY.ordinal) }
    @Test fun testStudyFeatureOrdinalArchive() { assertEquals(3, StudyFeature.ARCHIVE.ordinal) }
    @Test fun testStudyFeatureOrdinalIosSensor() { assertEquals(4, StudyFeature.IOS_SENSOR.ordinal) }
    @Test fun testStudyFeatureOrdinalAndroidSensor() { assertEquals(5, StudyFeature.ANDROID_SENSOR.ordinal) }
    @Test fun testStudyFeatureOrdinalChronicleSurveys() { assertEquals(6, StudyFeature.CHRONICLE_SURVEYS.ordinal) }
    @Test fun testStudyFeatureOrdinalChronicle() { assertEquals(7, StudyFeature.CHRONICLE.ordinal) }
    @Test fun testStudyFeatureOrdinalChronicleDataCollection() { assertEquals(8, StudyFeature.CHRONICLE_DATA_COLLECTION.ordinal) }
    @Test fun testStudyFeatureValueOfAppUsage() { assertEquals(StudyFeature.APP_USAGE, StudyFeature.valueOf("APP_USAGE")) }
    @Test fun testStudyFeatureValueOfInvalid() {
        try {
            StudyFeature.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== StudyLifecycleStatus (3 values) =====
    @Test fun testStudyLifecycleStatusCount() { assertEquals(3, StudyLifecycleStatus.values().size) }
    @Test fun testStudyLifecycleStatusActive() { assertEquals("ACTIVE", StudyLifecycleStatus.ACTIVE.name) }
    @Test fun testStudyLifecycleStatusArchived() { assertEquals("ARCHIVED", StudyLifecycleStatus.ARCHIVED.name) }
    @Test fun testStudyLifecycleStatusScheduledForDeletion() {
        assertEquals("SCHEDULED_FOR_DELETION", StudyLifecycleStatus.SCHEDULED_FOR_DELETION.name)
    }
    @Test fun testStudyLifecycleStatusOrdinalActive() { assertEquals(0, StudyLifecycleStatus.ACTIVE.ordinal) }
    @Test fun testStudyLifecycleStatusOrdinalArchived() { assertEquals(1, StudyLifecycleStatus.ARCHIVED.ordinal) }
    @Test fun testStudyLifecycleStatusOrdinalScheduledForDeletion() { assertEquals(2, StudyLifecycleStatus.SCHEDULED_FOR_DELETION.ordinal) }
    @Test fun testStudyLifecycleStatusValueOfActive() { assertEquals(StudyLifecycleStatus.ACTIVE, StudyLifecycleStatus.valueOf("ACTIVE")) }
    @Test fun testStudyLifecycleStatusValueOfInvalid() {
        try {
            StudyLifecycleStatus.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== ParticipantDataType (17 values) =====
    @Test fun testParticipantDataTypeCount() { assertEquals(17, ParticipantDataType.values().size) }
    @Test fun testParticipantDataTypeUsageEvents() { assertEquals("UsageEvents", ParticipantDataType.UsageEvents.name) }
    @Test fun testParticipantDataTypePreprocessed() { assertEquals("Preprocessed", ParticipantDataType.Preprocessed.name) }
    @Test fun testParticipantDataTypeAppUsageSurvey() { assertEquals("AppUsageSurvey", ParticipantDataType.AppUsageSurvey.name) }
    @Test fun testParticipantDataTypeIOSSensor() { assertEquals("IOSSensor", ParticipantDataType.IOSSensor.name) }
    @Test fun testParticipantDataTypeAndroidSensor() { assertEquals("AndroidSensor", ParticipantDataType.AndroidSensor.name) }
    @Test fun testParticipantDataTypeOrdinalUsageEvents() { assertEquals(0, ParticipantDataType.UsageEvents.ordinal) }
    @Test fun testParticipantDataTypeOrdinalPreprocessed() { assertEquals(1, ParticipantDataType.Preprocessed.ordinal) }
    @Test fun testParticipantDataTypeOrdinalAppUsageSurvey() { assertEquals(2, ParticipantDataType.AppUsageSurvey.ordinal) }
    @Test fun testParticipantDataTypeOrdinalIOSSensor() { assertEquals(3, ParticipantDataType.IOSSensor.ordinal) }
    @Test fun testParticipantDataTypeOrdinalAndroidSensor() { assertEquals(4, ParticipantDataType.AndroidSensor.ordinal) }
    @Test fun testParticipantDataTypeAndroidCollectionExports() {
        assertEquals(
            listOf(
                "SensorAvailability",
                "BatteryTelemetry",
                "InteractionEvents",
                "AudioActivity",
                "AudioContent",
                "NotificationActivity",
                "SleepEvents",
                "ActivityRecognition",
                "HealthMetrics",
                "ConnectivityState",
                "AppNetworkUsage",
                "DeviceSettings",
            ),
            ParticipantDataType.values().drop(5).map(ParticipantDataType::name),
        )
    }
    @Test fun testParticipantDataTypeValueOfUsageEvents() {
        assertEquals(ParticipantDataType.UsageEvents, ParticipantDataType.valueOf("UsageEvents"))
    }
    @Test fun testParticipantDataTypeValueOfInvalid() {
        try {
            ParticipantDataType.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== StudySettingType (10 values) =====
    @Test fun testStudySettingTypeCount() { assertEquals(10, StudySettingType.values().size) }
    @Test fun testStudySettingTypeDataCollection() { assertEquals("DataCollection", StudySettingType.DataCollection.name) }
    @Test fun testStudySettingTypeNotifications() { assertEquals("Notifications", StudySettingType.Notifications.name) }
    @Test fun testStudySettingTypeSensor() { assertEquals("Sensor", StudySettingType.Sensor.name) }
    @Test fun testStudySettingTypeTimeUseDiary() { assertEquals("TimeUseDiary", StudySettingType.TimeUseDiary.name) }
    @Test fun testStudySettingTypeSurvey() { assertEquals("Survey", StudySettingType.Survey.name) }
    @Test fun testStudySettingTypeAndroidSensor() { assertEquals("AndroidSensor", StudySettingType.AndroidSensor.name) }
    @Test fun testStudySettingTypeDataQuality() { assertEquals("DataQuality", StudySettingType.DataQuality.name) }
    @Test fun testStudySettingTypePipeline() { assertEquals("Pipeline", StudySettingType.Pipeline.name) }
    @Test fun testStudySettingTypeEncryption() { assertEquals("Encryption", StudySettingType.Encryption.name) }
    @Test fun testStudySettingTypeParticipantPolicy() {
        assertEquals("ParticipantPolicy", StudySettingType.ParticipantPolicy.name)
    }
    @Test fun testStudySettingTypeOrdinalDataCollection() { assertEquals(0, StudySettingType.DataCollection.ordinal) }
    @Test fun testStudySettingTypeOrdinalPipeline() { assertEquals(7, StudySettingType.Pipeline.ordinal) }
    @Test fun testStudySettingTypeOrdinalEncryption() { assertEquals(8, StudySettingType.Encryption.ordinal) }
    @Test fun testStudySettingTypeOrdinalParticipantPolicy() { assertEquals(9, StudySettingType.ParticipantPolicy.ordinal) }
    @Test fun testStudySettingTypeValueOfSensor() { assertEquals(StudySettingType.Sensor, StudySettingType.valueOf("Sensor")) }
    @Test fun testStudySettingTypeValueOfInvalid() {
        try {
            StudySettingType.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== ViolationReason (3 values) =====
    @Test fun testViolationReasonCount() { assertEquals(3, ViolationReason.values().size) }
    @Test fun testViolationReasonNoDataUploaded() { assertEquals("NO_DATA_UPLOADED", ViolationReason.NO_DATA_UPLOADED.name) }
    @Test fun testViolationReasonNoRecentDataUploaded() { assertEquals("NO_RECENT_DATA_UPLOADED", ViolationReason.NO_RECENT_DATA_UPLOADED.name) }
    @Test fun testViolationReasonNotEnrolled() { assertEquals("NOT_ENROLLED", ViolationReason.NOT_ENROLLED.name) }
    @Test fun testViolationReasonOrdinalNoDataUploaded() { assertEquals(0, ViolationReason.NO_DATA_UPLOADED.ordinal) }
    @Test fun testViolationReasonValueOfNotEnrolled() { assertEquals(ViolationReason.NOT_ENROLLED, ViolationReason.valueOf("NOT_ENROLLED")) }
    @Test fun testViolationReasonValueOfInvalid() {
        try {
            ViolationReason.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
