package com.openlattice.chronicle.authorization

import com.openlattice.chronicle.util.RetrofitBuilders
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class RetrofitResponseDecodingTest {
    @Test fun optionalRoleFieldsDeserializePopulatedAndEmpty() {
        val mapper = RetrofitBuilders.mapper
        for (description in listOf("null", "\"description\"")) {
            val role = mapper.readValue("""{"@class":"Role","id":"${UUID.randomUUID()}","organizationId":"${UUID.randomUUID()}",
                "principal":{"type":"ROLE","id":"role"},"title":"Role","description":$description}""", Role::class.java)
            assertEquals(if (description == "null") "" else "description", role.description)
        }
    }
}
