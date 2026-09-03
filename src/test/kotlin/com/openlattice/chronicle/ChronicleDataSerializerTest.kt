package com.openlattice.chronicle

import com.openlattice.chronicle.android.ChronicleData
import com.openlattice.chronicle.android.ChronicleUsageEvent
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import org.junit.Assert
import org.junit.Test
import java.time.OffsetDateTime
import java.util.*

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@getmethodic.com&gt;
 */
class ChronicleDataSerializerTest : AbstractJacksonSerializationTest<ChronicleData>() {
    override val sampleData: ChronicleData
        get() = ChronicleData((0 until 10).map {
            ChronicleUsageEvent(
                UUID.randomUUID(),
                RandomStringUtils.secure().nextAlphanumeric(5),
                RandomStringUtils.secure().nextAlphanumeric(5),
                RandomStringUtils.secure().nextAlphanumeric(5),
                RandomUtils.secure().randomInt(0, 100),
                OffsetDateTime.now(),
                TimeZone.getDefault().id,
                RandomStringUtils.secure().nextAlphanumeric(5),
                RandomStringUtils.secure().nextAlphanumeric(5)
            )
        })


    override val clazz: Class<ChronicleData>
        get() = ChronicleData::class.java

    override fun logResult(result: SerializationResult<ChronicleData>) {
        logger.info(result.jsonString)
    }

    @Test
    fun testHashCode() {
        val a = sampleData
        val b = sampleData
        if (a == b) {
            Assert.assertEquals(a.hashCode(), b.hashCode())
        } else {
            Assert.assertNotEquals(a.hashCode(), b.hashCode())
        }
    }
}
