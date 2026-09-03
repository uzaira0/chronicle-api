package com.openlattice.chronicle.models

import com.openlattice.chronicle.android.AndroidDeviceSensorAvailability
import com.openlattice.chronicle.android.AndroidSensorSample
import com.openlattice.chronicle.android.AndroidSensorSetting
import com.openlattice.chronicle.android.AndroidSensorType
import com.openlattice.chronicle.android.ChronicleData
import com.openlattice.chronicle.android.ChronicleUsageEvent
import com.openlattice.chronicle.android.ChronicleUsageEventType
import com.openlattice.chronicle.android.fromInteractionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class AndroidModelsTest {

    // ===== AndroidSensorSetting =====
    @Test fun testAndroidSensorSettingDefaultSensors() { assertTrue(AndroidSensorSetting().sensors.isEmpty()) }
    @Test fun testAndroidSensorSettingDefaultSamplingRate() { assertEquals(5, AndroidSensorSetting().samplingRateHz) }
    @Test fun testAndroidSensorSettingDefaultDutyCycleActive() { assertEquals(30, AndroidSensorSetting().dutyCycleActiveSeconds) }
    @Test fun testAndroidSensorSettingDefaultDutyCyclePeriod() { assertEquals(300, AndroidSensorSetting().dutyCyclePeriodSeconds) }
    @Test fun testAndroidSensorSettingNoSensorsCompanion() {
        val noSensors = AndroidSensorSetting.NO_SENSORS
        assertTrue(noSensors.sensors.isEmpty())
    }
    @Test fun testAndroidSensorSettingCustomSensors() {
        val setting = AndroidSensorSetting(sensors = setOf(AndroidSensorType.accelerometer, AndroidSensorType.gyroscope))
        assertEquals(2, setting.sensors.size)
    }
    @Test fun testAndroidSensorSettingCustomSamplingRate() {
        val setting = AndroidSensorSetting(samplingRateHz = 50)
        assertEquals(50, setting.samplingRateHz)
    }
    @Test fun testAndroidSensorSettingEquality() {
        assertEquals(AndroidSensorSetting(), AndroidSensorSetting())
    }
    @Test fun testAndroidSensorSettingInequality() {
        assertNotEquals(
            AndroidSensorSetting(samplingRateHz = 5),
            AndroidSensorSetting(samplingRateHz = 10)
        )
    }
    @Test fun testAndroidSensorSettingToString() { assertNotNull(AndroidSensorSetting().toString()) }

    // ===== AndroidDeviceSensorAvailability =====
    @Test fun testAndroidDeviceSensorAvailabilityDefaults() {
        val a = AndroidDeviceSensorAvailability()
        assertEquals("", a.participantId)
        assertEquals("", a.deviceId)
        assertTrue(a.availableSensors.isEmpty())
        assertTrue(a.unavailableSensors.isEmpty())
        assertNull(a.reportedAt)
    }
    @Test fun testAndroidDeviceSensorAvailabilityCustom() {
        val now = OffsetDateTime.now()
        val a = AndroidDeviceSensorAvailability(
            participantId = "p1",
            deviceId = "d1",
            availableSensors = setOf(AndroidSensorType.accelerometer),
            unavailableSensors = setOf(AndroidSensorType.proximity),
            reportedAt = now
        )
        assertEquals("p1", a.participantId)
        assertEquals("d1", a.deviceId)
        assertEquals(1, a.availableSensors.size)
        assertEquals(1, a.unavailableSensors.size)
        assertEquals(now, a.reportedAt)
    }
    @Test fun testAndroidDeviceSensorAvailabilityEquality() {
        assertEquals(AndroidDeviceSensorAvailability(), AndroidDeviceSensorAvailability())
    }
    @Test fun testAndroidDeviceSensorAvailabilityToString() { assertNotNull(AndroidDeviceSensorAvailability().toString()) }

    // ===== AndroidSensorSample =====
    @Test fun testAndroidSensorSampleConstruction() {
        val id = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val sample = AndroidSensorSample(
            id = id,
            sensor = AndroidSensorType.accelerometer,
            timestamp = now,
            timezone = "America/Chicago"
        )
        assertEquals(id, sample.id)
        assertEquals(AndroidSensorType.accelerometer, sample.sensor)
        assertEquals(now, sample.timestamp)
        assertEquals("America/Chicago", sample.timezone)
    }
    @Test fun testAndroidSensorSampleDefaultX() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC")
        assertNull(sample.x)
    }
    @Test fun testAndroidSensorSampleDefaultY() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC")
        assertNull(sample.y)
    }
    @Test fun testAndroidSensorSampleDefaultZ() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC")
        assertNull(sample.z)
    }
    @Test fun testAndroidSensorSampleDefaultW() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.rotationVector, OffsetDateTime.now(), "UTC")
        assertNull(sample.w)
    }
    @Test fun testAndroidSensorSampleDefaultAccuracy() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC")
        assertNull(sample.accuracy)
    }
    @Test fun testAndroidSensorSampleCustomValues() {
        val sample = AndroidSensorSample(
            UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC",
            x = 1.0f, y = 2.0f, z = 3.0f, accuracy = 2
        )
        assertEquals(1.0f, sample.x!!, 0.001f)
        assertEquals(2.0f, sample.y!!, 0.001f)
        assertEquals(3.0f, sample.z!!, 0.001f)
        assertEquals(2, sample.accuracy)
    }
    @Test fun testAndroidSensorSampleRawValues() {
        val sample = AndroidSensorSample(
            UUID.randomUUID(), AndroidSensorType.samsungGripWifi, OffsetDateTime.now(), "UTC",
            values = listOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f)
        )
        assertEquals(5, sample.values.size)
        assertEquals(5.0f, sample.values[4], 0.001f)
    }
    @Test fun testAndroidSensorSampleToString() {
        val sample = AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC")
        assertNotNull(sample.toString())
    }

    // ===== ChronicleUsageEvent =====
    @Test fun testChronicleUsageEventConstruction() {
        val studyId = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val event = ChronicleUsageEvent(
            studyId = studyId,
            participantId = "p1",
            appPackageName = "com.example.app",
            interactionType = "Move to Foreground",
            timestamp = now,
            timezone = "UTC",
            user = "user1",
            applicationLabel = "ExampleApp"
        )
        assertEquals(studyId, event.studyId)
        assertEquals("p1", event.participantId)
        assertEquals("com.example.app", event.appPackageName)
        assertEquals("Move to Foreground", event.interactionType)
        assertEquals(now, event.timestamp)
    }
    @Test fun testChronicleUsageEventTypeFromInteractionMoveToForeground() {
        assertEquals(ChronicleUsageEventType.MOVE_TO_FOREGROUND.value, fromInteractionType("Move to Foreground"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionMoveToBackground() {
        assertEquals(ChronicleUsageEventType.MOVE_TO_BACKGROUND.value, fromInteractionType("Move to Background"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionNone() {
        assertEquals(ChronicleUsageEventType.NONE.value, fromInteractionType("None"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionActivityPaused() {
        assertEquals(ChronicleUsageEventType.ACTIVITY_PAUSED.value, fromInteractionType("Activity Paused"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionActivityResumed() {
        assertEquals(ChronicleUsageEventType.ACTIVITY_RESUMED.value, fromInteractionType("Activity Resumed"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionActivityStopped() {
        assertEquals(ChronicleUsageEventType.ACTIVITY_STOPPED.value, fromInteractionType("Activity Stopped"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionConfigChange() {
        assertEquals(ChronicleUsageEventType.CONFIGURATION_CHANGE.value, fromInteractionType("Configuration Change"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionDeviceShutdown() {
        assertEquals(ChronicleUsageEventType.DEVICE_SHUTDOWN.value, fromInteractionType("Device Shutdown"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionDeviceStartup() {
        assertEquals(ChronicleUsageEventType.DEVICE_STARTUP.value, fromInteractionType("Device Startup"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionBatteryCharging() {
        assertEquals(ChronicleUsageEventType.BATTERY_CHARGING.value, fromInteractionType("Battery Charging"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionBatteryDischarging() {
        assertEquals(ChronicleUsageEventType.BATTERY_DISCHARGING.value, fromInteractionType("Battery Discharging"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionPowerSaveModeOn() {
        assertEquals(ChronicleUsageEventType.POWER_SAVE_MODE_ON.value, fromInteractionType("Power Save Mode On"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionNetworkConnected() {
        assertEquals(ChronicleUsageEventType.NETWORK_CONNECTED.value, fromInteractionType("Network Connected"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionLowMemory() {
        assertEquals(ChronicleUsageEventType.LOW_MEMORY.value, fromInteractionType("Low Memory"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionUsageStat() {
        assertEquals(-1, fromInteractionType("Usage Stat"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionUnknown() {
        assertEquals(-1, fromInteractionType("some-unknown-type"))
    }
    @Test fun testChronicleUsageEventTypeFromInteractionUnknownImportance() {
        assertEquals(42, fromInteractionType("Unknown importance: 42"))
    }

    // ===== ChronicleData =====
    @Test fun testChronicleDataEmpty() {
        val data = ChronicleData(emptyList())
        assertEquals(0, data.size)
        assertTrue(data.isEmpty())
    }
    @Test fun testChronicleDataToString() {
        val data = ChronicleData(emptyList())
        assertEquals("[]", data.toString())
    }
    @Test fun testChronicleDataHashCodeConsistency() {
        val data1 = ChronicleData(emptyList())
        val data2 = ChronicleData(emptyList())
        assertEquals(data1.hashCode(), data2.hashCode())
    }
}
