package com.openlattice.chronicle.enums

import com.openlattice.chronicle.android.ChronicleUsageEventType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class UsageEventTypeTest {

    // ===== ChronicleUsageEventType (26 values) =====
    @Test fun testChronicleUsageEventTypeCount() { assertEquals(26, ChronicleUsageEventType.values().size) }

    // Name tests
    @Test fun testNoneName() { assertEquals("NONE", ChronicleUsageEventType.NONE.name) }
    @Test fun testMoveToForegroundName() { assertEquals("MOVE_TO_FOREGROUND", ChronicleUsageEventType.MOVE_TO_FOREGROUND.name) }
    @Test fun testMoveToBackgroundName() { assertEquals("MOVE_TO_BACKGROUND", ChronicleUsageEventType.MOVE_TO_BACKGROUND.name) }
    @Test fun testActivityPausedName() { assertEquals("ACTIVITY_PAUSED", ChronicleUsageEventType.ACTIVITY_PAUSED.name) }
    @Test fun testActivityResumedName() { assertEquals("ACTIVITY_RESUMED", ChronicleUsageEventType.ACTIVITY_RESUMED.name) }
    @Test fun testConfigurationChangeName() { assertEquals("CONFIGURATION_CHANGE", ChronicleUsageEventType.CONFIGURATION_CHANGE.name) }
    @Test fun testDeviceShutdownName() { assertEquals("DEVICE_SHUTDOWN", ChronicleUsageEventType.DEVICE_SHUTDOWN.name) }
    @Test fun testDeviceStartupName() { assertEquals("DEVICE_STARTUP", ChronicleUsageEventType.DEVICE_STARTUP.name) }
    @Test fun testUserInteractionName() { assertEquals("USER_INTERACTION", ChronicleUsageEventType.USER_INTERACTION.name) }
    @Test fun testShortcutInvocationName() { assertEquals("SHORTCUT_INVOCATION", ChronicleUsageEventType.SHORTCUT_INVOCATION.name) }
    @Test fun testScreenInteractiveName() { assertEquals("SCREEN_INTERACTIVE", ChronicleUsageEventType.SCREEN_INTERACTIVE.name) }
    @Test fun testScreenNonInteractiveName() { assertEquals("SCREEN_NON_INTERACTIVE", ChronicleUsageEventType.SCREEN_NON_INTERACTIVE.name) }
    @Test fun testKeyguardShownName() { assertEquals("KEYGUARD_SHOWN", ChronicleUsageEventType.KEYGUARD_SHOWN.name) }
    @Test fun testForegroundServiceStartName() { assertEquals("FOREGROUND_SERVICE_START", ChronicleUsageEventType.FOREGROUND_SERVICE_START.name) }
    @Test fun testForegroundServiceStopName() { assertEquals("FOREGROUND_SERVICE_STOP", ChronicleUsageEventType.FOREGROUND_SERVICE_STOP.name) }
    @Test fun testKeyguardHiddenName() { assertEquals("KEYGUARD_HIDDEN", ChronicleUsageEventType.KEYGUARD_HIDDEN.name) }
    @Test fun testActivityStoppedName() { assertEquals("ACTIVITY_STOPPED", ChronicleUsageEventType.ACTIVITY_STOPPED.name) }
    @Test fun testBatteryLowName() { assertEquals("BATTERY_LOW", ChronicleUsageEventType.BATTERY_LOW.name) }
    @Test fun testBatteryOkayName() { assertEquals("BATTERY_OKAY", ChronicleUsageEventType.BATTERY_OKAY.name) }
    @Test fun testNetworkConnectedName() { assertEquals("NETWORK_CONNECTED", ChronicleUsageEventType.NETWORK_CONNECTED.name) }
    @Test fun testNetworkDisconnectedName() { assertEquals("NETWORK_DISCONNECTED", ChronicleUsageEventType.NETWORK_DISCONNECTED.name) }
    @Test fun testLowMemoryName() { assertEquals("LOW_MEMORY", ChronicleUsageEventType.LOW_MEMORY.name) }

    // Value (int) tests
    @Test fun testNoneValue() { assertEquals(0, ChronicleUsageEventType.NONE.value) }
    @Test fun testMoveToForegroundValue() { assertEquals(1, ChronicleUsageEventType.MOVE_TO_FOREGROUND.value) }
    @Test fun testMoveToBackgroundValue() { assertEquals(2, ChronicleUsageEventType.MOVE_TO_BACKGROUND.value) }
    @Test fun testConfigurationChangeValue() { assertEquals(5, ChronicleUsageEventType.CONFIGURATION_CHANGE.value) }
    @Test fun testUserInteractionValue() { assertEquals(7, ChronicleUsageEventType.USER_INTERACTION.value) }
    @Test fun testShortcutInvocationValue() { assertEquals(8, ChronicleUsageEventType.SHORTCUT_INVOCATION.value) }
    @Test fun testScreenInteractiveValue() { assertEquals(15, ChronicleUsageEventType.SCREEN_INTERACTIVE.value) }
    @Test fun testScreenNonInteractiveValue() { assertEquals(16, ChronicleUsageEventType.SCREEN_NON_INTERACTIVE.value) }
    @Test fun testKeyguardShownValue() { assertEquals(17, ChronicleUsageEventType.KEYGUARD_SHOWN.value) }
    @Test fun testKeyguardHiddenValue() { assertEquals(18, ChronicleUsageEventType.KEYGUARD_HIDDEN.value) }
    @Test fun testForegroundServiceStartValue() { assertEquals(19, ChronicleUsageEventType.FOREGROUND_SERVICE_START.value) }
    @Test fun testForegroundServiceStopValue() { assertEquals(20, ChronicleUsageEventType.FOREGROUND_SERVICE_STOP.value) }
    @Test fun testActivityStoppedValue() { assertEquals(23, ChronicleUsageEventType.ACTIVITY_STOPPED.value) }
    @Test fun testDeviceShutdownValue() { assertEquals(26, ChronicleUsageEventType.DEVICE_SHUTDOWN.value) }
    @Test fun testDeviceStartupValue() { assertEquals(27, ChronicleUsageEventType.DEVICE_STARTUP.value) }
    @Test fun testBatteryLowValue() { assertEquals(0x00010001, ChronicleUsageEventType.BATTERY_LOW.value) }
    @Test fun testBatteryOkayValue() { assertEquals(0x00010002, ChronicleUsageEventType.BATTERY_OKAY.value) }
    @Test fun testBatteryChargingValue() { assertEquals(0x00010003, ChronicleUsageEventType.BATTERY_CHARGING.value) }
    @Test fun testBatteryDischargingValue() { assertEquals(0x00010004, ChronicleUsageEventType.BATTERY_DISCHARGING.value) }
    @Test fun testPowerSaveModeOnValue() { assertEquals(0x00010010, ChronicleUsageEventType.POWER_SAVE_MODE_ON.value) }
    @Test fun testPowerSaveModeOffValue() { assertEquals(0x00010011, ChronicleUsageEventType.POWER_SAVE_MODE_OFF.value) }
    @Test fun testNetworkConnectedValue() { assertEquals(0x00010020, ChronicleUsageEventType.NETWORK_CONNECTED.value) }
    @Test fun testNetworkDisconnectedValue() { assertEquals(0x00010021, ChronicleUsageEventType.NETWORK_DISCONNECTED.value) }
    @Test fun testLowMemoryValue() { assertEquals(0x00010030, ChronicleUsageEventType.LOW_MEMORY.value) }

    // Alias equivalence tests
    @Test fun testActivityPausedEqualsMoveToBackground() {
        assertEquals(ChronicleUsageEventType.MOVE_TO_BACKGROUND.value, ChronicleUsageEventType.ACTIVITY_PAUSED.value)
    }
    @Test fun testActivityResumedEqualsMoveToForeground() {
        assertEquals(ChronicleUsageEventType.MOVE_TO_FOREGROUND.value, ChronicleUsageEventType.ACTIVITY_RESUMED.value)
    }

    // valueOf tests
    @Test fun testValueOfNone() { assertEquals(ChronicleUsageEventType.NONE, ChronicleUsageEventType.valueOf("NONE")) }
    @Test fun testValueOfMoveToForeground() {
        assertEquals(ChronicleUsageEventType.MOVE_TO_FOREGROUND, ChronicleUsageEventType.valueOf("MOVE_TO_FOREGROUND"))
    }
    @Test fun testValueOfInvalid() {
        try {
            ChronicleUsageEventType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // Hex value cross-checks
    @Test fun testNoneHexValue() { assertEquals(0x00000000, ChronicleUsageEventType.NONE.value) }
    @Test fun testMoveToForegroundHexValue() { assertEquals(0x00000001, ChronicleUsageEventType.MOVE_TO_FOREGROUND.value) }
    @Test fun testMoveToBackgroundHexValue() { assertEquals(0x00000002, ChronicleUsageEventType.MOVE_TO_BACKGROUND.value) }
    @Test fun testDeviceShutdownHexValue() { assertEquals(0x0000001a, ChronicleUsageEventType.DEVICE_SHUTDOWN.value) }
    @Test fun testDeviceStartupHexValue() { assertEquals(0x0000001b, ChronicleUsageEventType.DEVICE_STARTUP.value) }
}
