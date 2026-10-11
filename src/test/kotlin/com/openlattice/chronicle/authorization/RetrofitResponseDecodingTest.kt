package com.openlattice.chronicle.authorization

import com.openlattice.chronicle.survey.AppUsage
import com.openlattice.chronicle.util.RetrofitBuilders
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class RetrofitResponseDecodingTest {
    @Test fun optionalRoleAndUsageFieldsDeserializePopulatedAndEmpty() {
        val mapper = RetrofitBuilders.mapper
        for (description in listOf("null", "\"description\"")) {
            val role = mapper.readValue("""{"@class":"Role","id":"${UUID.randomUUID()}","organizationId":"${UUID.randomUUID()}",
                "principal":{"type":"ROLE","id":"role"},"title":"Role","description":$description}""", Role::class.java)
            assertEquals(if (description == "null") "" else "description", role.description)
        }
        for (uploaded in listOf("null", "\"2026-10-07T23:45:00-05:00\"")) {
            val usage = mapper.readValue("""{"appPackageName":"sample","appLabel":null,"timestamp":"2026-10-07T23:45:00-05:00",
                "eventType":1,"timezone":"America/Chicago","uploadedAt":$uploaded}""", AppUsage::class.java)
            assertEquals(uploaded != "null", usage.uploadedAt.isPresent)
            assertEquals("2026-10-07T23:45-05:00", usage.timestamp.toString())
        }
    }
}
