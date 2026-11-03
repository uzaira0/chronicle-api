package com.openlattice.chronicle.enums

import com.openlattice.chronicle.android.AndroidSensorType
import com.openlattice.chronicle.sources.SourceDeviceType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class DeviceEnumsTest {

    // ===== AndroidSensorType (14 values) =====
    @Test fun testAndroidSensorTypeCount() { assertEquals(12, AndroidSensorType.values().size) }
    @Test fun testAndroidSensorTypeAccelerometer() { assertEquals("accelerometer", AndroidSensorType.accelerometer.name) }
    @Test fun testAndroidSensorTypeGyroscope() { assertEquals("gyroscope", AndroidSensorType.gyroscope.name) }
    @Test fun testAndroidSensorTypeMagnetometer() { assertEquals("magnetometer", AndroidSensorType.magnetometer.name) }
    @Test fun testAndroidSensorTypeGravity() { assertEquals("gravity", AndroidSensorType.gravity.name) }
    @Test fun testAndroidSensorTypeLinearAcceleration() { assertEquals("linearAcceleration", AndroidSensorType.linearAcceleration.name) }
    @Test fun testAndroidSensorTypeRotationVector() { assertEquals("rotationVector", AndroidSensorType.rotationVector.name) }
    @Test fun testAndroidSensorTypeStepCounter() { assertEquals("stepCounter", AndroidSensorType.stepCounter.name) }
    @Test fun testAndroidSensorTypeLight() { assertEquals("light", AndroidSensorType.light.name) }
    @Test fun testAndroidSensorTypeProximity() { assertEquals("proximity", AndroidSensorType.proximity.name) }
    @Test fun testAndroidSensorTypeSignificantMotion() { assertEquals("significantMotion", AndroidSensorType.significantMotion.name) }
    @Test fun testAndroidSensorTypeTiltDetector() { assertEquals("tiltDetector", AndroidSensorType.tiltDetector.name) }
    @Test fun testAndroidSensorTypeScreenOrientation() { assertEquals("screenOrientation", AndroidSensorType.screenOrientation.name) }
    @Test fun testAndroidSensorTypeOrdinalAccelerometer() { assertEquals(0, AndroidSensorType.accelerometer.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalGyroscope() { assertEquals(1, AndroidSensorType.gyroscope.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalMagnetometer() { assertEquals(2, AndroidSensorType.magnetometer.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalGravity() { assertEquals(3, AndroidSensorType.gravity.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalLinearAcceleration() { assertEquals(4, AndroidSensorType.linearAcceleration.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalRotationVector() { assertEquals(5, AndroidSensorType.rotationVector.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalStepCounter() { assertEquals(6, AndroidSensorType.stepCounter.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalLight() { assertEquals(7, AndroidSensorType.light.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalProximity() { assertEquals(8, AndroidSensorType.proximity.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalSignificantMotion() { assertEquals(9, AndroidSensorType.significantMotion.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalTiltDetector() { assertEquals(10, AndroidSensorType.tiltDetector.ordinal) }
    @Test fun testAndroidSensorTypeOrdinalScreenOrientation() { assertEquals(11, AndroidSensorType.screenOrientation.ordinal) }
    @Test fun testAndroidSensorTypeValueOfAccelerometer() {
        assertEquals(AndroidSensorType.accelerometer, AndroidSensorType.valueOf("accelerometer"))
    }
    @Test fun testAndroidSensorTypeValueOfInvalid() {
        try {
            AndroidSensorType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== SourceDeviceType (3 values) =====
    @Test fun testSourceDeviceTypeCount() { assertEquals(3, SourceDeviceType.values().size) }
    @Test fun testSourceDeviceTypeNone() { assertEquals("None", SourceDeviceType.None.name) }
    @Test fun testSourceDeviceTypeAndroid() { assertEquals("Android", SourceDeviceType.Android.name) }
    @Test fun testSourceDeviceTypeIos() { assertEquals("Ios", SourceDeviceType.Ios.name) }
    @Test fun testSourceDeviceTypeOrdinalNone() { assertEquals(0, SourceDeviceType.None.ordinal) }
    @Test fun testSourceDeviceTypeOrdinalAndroid() { assertEquals(1, SourceDeviceType.Android.ordinal) }
    @Test fun testSourceDeviceTypeOrdinalIos() { assertEquals(2, SourceDeviceType.Ios.ordinal) }
    @Test fun testSourceDeviceTypeValueOfNone() { assertEquals(SourceDeviceType.None, SourceDeviceType.valueOf("None")) }
    @Test fun testSourceDeviceTypeValueOfInvalid() {
        try {
            SourceDeviceType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
