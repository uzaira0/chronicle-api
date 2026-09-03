package com.openlattice.chronicle.enums

import com.openlattice.chronicle.ids.IdConstants
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class IdConstantsEnumTest {

    @Test fun testIdConstantsCount() { assertEquals(9, IdConstants.values().size) }

    @Test fun testUninitializedName() { assertEquals("UNINITIALIZED", IdConstants.UNINITIALIZED.name) }
    @Test fun testChronicleName() { assertEquals("CHRONICLE", IdConstants.CHRONICLE.name) }
    @Test fun testSystemOrganizationName() { assertEquals("SYSTEM_ORGANIZATION", IdConstants.SYSTEM_ORGANIZATION.name) }
    @Test fun testLastWriteKeyIdName() { assertEquals("LAST_WRITE_KEY_ID", IdConstants.LAST_WRITE_KEY_ID.name) }
    @Test fun testCountIdName() { assertEquals("COUNT_ID", IdConstants.COUNT_ID.name) }
    @Test fun testIdIdName() { assertEquals("ID_ID", IdConstants.ID_ID.name) }
    @Test fun testLastIndexIdName() { assertEquals("LAST_INDEX_ID", IdConstants.LAST_INDEX_ID.name) }
    @Test fun testLastLinkIdName() { assertEquals("LAST_LINK_ID", IdConstants.LAST_LINK_ID.name) }
    @Test fun testLastWriteIdName() { assertEquals("LAST_WRITE_ID", IdConstants.LAST_WRITE_ID.name) }

    @Test fun testUninitializedId() { assertEquals(UUID(0L, 0L), IdConstants.UNINITIALIZED.id) }
    @Test fun testChronicleId() { assertEquals(UUID(0L, 1L), IdConstants.CHRONICLE.id) }
    @Test fun testSystemOrganizationId() { assertEquals(UUID(0L, 2L), IdConstants.SYSTEM_ORGANIZATION.id) }
    @Test fun testLastWriteKeyIdId() { assertEquals(UUID(0L, 3L), IdConstants.LAST_WRITE_KEY_ID.id) }
    @Test fun testCountIdId() { assertEquals(UUID(0L, 4L), IdConstants.COUNT_ID.id) }
    @Test fun testIdIdId() { assertEquals(UUID(0L, 5L), IdConstants.ID_ID.id) }
    @Test fun testLastIndexIdId() { assertEquals(UUID(0L, 6L), IdConstants.LAST_INDEX_ID.id) }
    @Test fun testLastLinkIdId() { assertEquals(UUID(0L, 7L), IdConstants.LAST_LINK_ID.id) }
    @Test fun testLastWriteIdId() { assertEquals(UUID(0L, 8L), IdConstants.LAST_WRITE_ID.id) }

    @Test fun testReservedIdsBase() { assertEquals(9L, IdConstants.RESERVED_IDS_BASE) }

    @Test fun testUninitializedMostSignificantBits() { assertEquals(0L, IdConstants.UNINITIALIZED.id.mostSignificantBits) }
    @Test fun testUninitializedLeastSignificantBits() { assertEquals(0L, IdConstants.UNINITIALIZED.id.leastSignificantBits) }
    @Test fun testChronicleLeastSignificantBits() { assertEquals(1L, IdConstants.CHRONICLE.id.leastSignificantBits) }
}
